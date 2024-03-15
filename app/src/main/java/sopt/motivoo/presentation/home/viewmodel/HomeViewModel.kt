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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.repository.FirebaseRepository
import sopt.motivoo.domain.repository.HomeRepository
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
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
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

    fun getMyStepCountFlow(userId: Long, stepCountGoal: Int) {
        viewModelScope.launch {
            stepCountRepository.myStepCount.stateIn(
                initialValue = stepCountRepository.getMyStepCount(),
                scope = this,
                started = SharingStarted.WhileSubscribed(5000)
            ).collect { stepCountFlow ->
                if (stepCountFlow != -1) {
                    stepCount.value = stepCountFlow
                    firebaseRepository.setUserStepCount(userId, stepCountFlow)
                    if (isCompletedMission.value == false && stepCountFlow >= stepCountGoal && stepCountGoal != 0) {
                        setHomeState(HomeState.CompletedStepCount)
                    }
                }
            }
        }
    }

    fun getOtherStepCountFlow(otherUserId: Int, otherStepCountGoal: Int) {
        viewModelScope.launch {
            firebaseRepository.getUpdatedStepCount(otherUserId.toLong()).stateIn(
                initialValue = firebaseRepository.getStepCount(otherUserId.toLong())?.toInt(),
                scope = this,
                started = SharingStarted.WhileSubscribed(50000)
            ).collect { otherStepCountFlow ->
                if (otherStepCountFlow != null) {
                    otherStepCount.value = otherStepCountFlow.toInt()
                    if (isCompletedMission.value == true && otherStepCountFlow >= otherStepCountGoal && otherStepCountGoal != 0) {
                        setHomeState(HomeState.HighFive)
                    }
                }
            }
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
                    postMissionToday(missionId.toInt())
                }
            }

            HomeIntent.SecondSelectMission -> {
                missionChoiceData.value?.missionChoiceList?.get(1)?.missionId?.let { missionId ->
                    postMissionToday(missionId.toInt())
                }
            }
        }
    }

    fun postMissionTodayChoice() {
        viewModelScope.launch {
            setHomeState(HomeState.Loading)
            repository.postMissionTodayChoice()?.let { missionData ->
                transactionWithMissionChoiceData(missionData)
                patchHome()
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

    private fun patchHome() {
        viewModelScope.launch {
            repository.patchHome()?.let { homeData ->
                getMyStepCountFlow(
                    homeData.userId,
                    homeData.userGoalStepCount
                )
                getOtherStepCountFlow(
                    homeData.opponentUserId.toInt(),
                    homeData.opponentUserGoalStepCount
                )

                if (stepCountRepository.getMyStepCount() != -1 && userRepository.getUserId() != -1) {
                    val stepCount = stepCountRepository.getMyStepCount()
                    val userId = userRepository.getUserId()
                    firebaseRepository.setUserStepCount(userId.toLong(), stepCount)
                } else {
                    if (stepCountRepository.getMyStepCount() == -1) {
                        firebaseRepository.getStepCount(homeData.userId)?.let {
                            stepCountRepository.setMyStepCount(it.toInt())
                        }
                    }
                    userRepository.setUserId(homeData.userId.toInt())
                }

                setHomeState(HomeState.FetchHomeData(homeData))

                if (homeData.isOpponentUserWithdraw) {
                    setHomeState(HomeState.FailMatching)
                    return@launch
                }

                firebaseRepository.getStepCount(homeData.opponentUserId)?.let {
                    if (!homeData.isStepCountCompleted && homeData.isMissionImageCompleted && homeData.opponentUserGoalStepCount != 0 &&
                        it >= homeData.opponentUserGoalStepCount
                    ) {
                        setHomeState(HomeState.HighFive)
                        return@launch
                    }
                }

                if (homeData.isStepCountCompleted && !homeData.isMissionImageCompleted) {
                    setHomeState(HomeState.CompletedStepCount)
                }

                if (homeData.isMissionImageCompleted) {
                    setHomeState(HomeState.CompletedMission)
                }
            }
        }
    }

    private fun postMissionToday(missionId: Int) {
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
