package sopt.motivoo.presentation.home.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.utils.MotivooUserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.repository.FirebaseRepository
import sopt.motivoo.domain.repository.HomeRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.domain.repository.UserRepository
import sopt.motivoo.presentation.home.HomeIntent
import sopt.motivoo.presentation.home.HomePictureState
import sopt.motivoo.presentation.home.HomeState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val stepCountRepository: StepCountRepository,
    private val onboardingRepository: OnboardingRepository,
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    val userId = MutableLiveData<Int>()
    val otherUserId = MutableLiveData<Int>()
    val stepCount = MutableLiveData<Int>()
    val otherStepCount = MutableLiveData<Int>()
    val stepCountGoal = MutableLiveData<Int>()
    val otherStepCountGoal = MutableLiveData<Int>()
    val userType = MutableLiveData<MotivooUserType>()
    val anotherUserType = MutableLiveData<MotivooUserType>()
    val isPermissionGranted = MutableLiveData<Boolean>()
    val isMissionChoiceFinished = MutableLiveData<Boolean>()
    val isCompletedStepCount = MutableLiveData<Boolean>()
    val isCompletedMission = MutableLiveData<Boolean>()
    val isHighFive = MutableLiveData<Boolean>()

    private val _missionChoiceData = MutableLiveData<MissionChoiceData>()
    val missionChoiceData: LiveData<MissionChoiceData> = _missionChoiceData

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val _homePictureState =
        MutableStateFlow<HomePictureState>(HomePictureState.Idle)
    val homePictureState: StateFlow<HomePictureState> = _homePictureState.asStateFlow()

    fun getMyStepCountFlow() {
        viewModelScope.launch {
            stepCountRepository.myStepCount.stateIn(
                initialValue = stepCountRepository.myStepCount.first(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            ).collect { stepCountFlow ->
                stepCount.value = stepCountFlow
                firebaseRepository.setUserStepCount(
                    userId.value?.toLong() ?: return@collect,
                    stepCountFlow
                )
                stepCountGoal.value?.let {
                    if (isCompletedMission.value == false && stepCountFlow >= it && it != 0) {
                        setHomeState(HomeState.CompletedStepCount)
                    }
                }
            }
        }
    }

    private suspend fun getOtherStepCountFlow(otherUserId: Int) {
        firebaseRepository.getUpdatedStepCount(otherUserId.toLong()).stateIn(
            initialValue = firebaseRepository.getStepCount(otherUserId.toLong()).first(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(50000)
        ).collect {
            this@HomeViewModel.otherStepCount.value = it
        }
    }

    fun setHomeState(homeState: HomeState) {
        _homeState.value = homeState
    }

    private fun setHomePictureState(homePictureState: HomePictureState) {
        _homePictureState.value = homePictureState
    }

    fun handleHomeIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            HomeIntent.FirstSelectMission -> {
                missionChoiceData.value?.missionChoiceList?.get(0)?.missionId?.let { missionId ->
                    setHomeState(HomeState.SelectedMission(missionId.toInt()))
                }
            }

            HomeIntent.SecondSelectMission -> {
                missionChoiceData.value?.missionChoiceList?.get(1)?.missionId?.let { missionId ->
                    setHomeState(HomeState.SelectedMission(missionId.toInt()))
                }
            }
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            setHomeState(HomeState.Loading)
            onboardingRepository.getInviteCode().onSuccess { matchedItem ->
                transactionWithUserId(matchedItem.userId, matchedItem.opponentUserId)
                postMissionTodayChoice()
            }.onFailure {
                // TODO :: 상대방 유저가 회원 탈퇴할 경우를 고려
            }
        }
    }

    private fun transactionWithUserId(myUserId: Int, otherUserId: Int) {
        viewModelScope.launch {
            userRepository.setUserId(myUserId)
            this@HomeViewModel.userId.value = myUserId
            this@HomeViewModel.otherUserId.value = otherUserId
            firebaseRepository.getStepCount(myUserId.toLong()).first().let {
                stepCount.value = it
                stepCountRepository.setMyStepCount(it)
            }
            firebaseRepository.getStepCount(otherUserId.toLong()).first().let {
                otherStepCount.value = it
            }
            getOtherStepCountFlow(otherUserId)
        }
    }

    private fun postMissionTodayChoice() {
        viewModelScope.launch {
            repository.postMissionTodayChoice()?.let { missionData ->
                transactionWithMissionChoiceData(missionData)
                if (stepCountRepository.myStepCount.first() == -1) {
                    userId.value?.let {
                        stepCountRepository.setMyStepCount(
                            firebaseRepository.getStepCount(it.toLong()).first()
                        )
                    }
                }
                otherUserId.value?.let {
                    patchHome(
                        stepCountRepository.myStepCount.first(),
                        firebaseRepository.getStepCount(it.toLong()).first()
                    )
                }
            }
        }
    }

    private fun transactionWithMissionChoiceData(missionData: MissionChoiceData) {
        _missionChoiceData.value = missionData
        isMissionChoiceFinished.value = missionData.isChoiceFinished
        when (missionData.isChoiceFinished) {
            true -> setHomeState(HomeState.SelectedMissionData(missionData.todayMission))
            false -> setHomeState(
                HomeState.UnSelectedMissionData(
                    missionData.missionChoiceList,
                    missionData.date
                )
            )
        }
    }

    private fun patchHome(stepCount: Int, otherStepCount: Int) {
        viewModelScope.launch {
            repository.patchHome(stepCount, otherStepCount)?.let { homeData ->
                stepCountGoal.value = homeData.userGoalStepCount
                otherStepCountGoal.value = homeData.opponentUserGoalStepCount
                isCompletedMission.value = homeData.isMissionImageCompleted

                setHomeState(HomeState.FetchHomeData(homeData))

                if (homeData.isOpponentUserWithdraw) {
                    setHomeState(HomeState.FailMatching)
                    return@launch
                }

                if (homeData.isStepCountCompleted && homeData.isMissionImageCompleted && homeData.opponentUserGoalStepCount != 0 && (otherStepCount >= homeData.opponentUserGoalStepCount)) {
                    setHomeState(HomeState.HighFive)
                    return@launch
                }

                if (homeData.isStepCountCompleted && !homeData.isMissionImageCompleted) {
                    setHomeState(HomeState.CompletedStepCount)
                }

                if (homeData.isMissionImageCompleted) {
                    if (homeData.opponentUserGoalStepCount != 0 && otherStepCount >= homeData.opponentUserGoalStepCount) {
                        setHomeState(HomeState.HighFive)
                    } else {
                        setHomeState(HomeState.CompletedMission)
                    }
                }
            }
        }
    }

    fun postMissionToday(missionId: Int) {
        viewModelScope.launch {
            if (repository.postMissionToday(missionId) != null) {
                postMissionTodayChoice()
            }
        }
    }

    fun getMissionImage(imagePrefix: String, pictureBitmap: Bitmap) {
        viewModelScope.launch {
            repository.getMissionImage(imagePrefix)?.let { missionImageData ->
                setHomePictureState(
                    HomePictureState.SuccessMissionData(
                        missionImageData.imgPresignedUrl,
                        missionImageData.fileName,
                        pictureBitmap
                    )
                )
            }
        }
    }

    fun patchMissionImage(fileName: String, pictureBitmap: Bitmap) {
        viewModelScope.launch {
            repository.patchMissionImage(fileName)?.let {
                setHomePictureState(HomePictureState.SuccessImageUpload(pictureBitmap))
            }
        }
    }

    fun uploadPhoto(url: String, fileName: String, pictureBitmap: Bitmap) {
        viewModelScope.launch {
            repository.uploadPhoto(url, pictureBitmap)?.let {
                setHomePictureState(HomePictureState.UploadFile(fileName, pictureBitmap))
            }
        }
    }
}
