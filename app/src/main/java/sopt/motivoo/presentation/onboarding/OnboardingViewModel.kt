package sopt.motivoo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.presentation.type.FrequencyType
import sopt.motivoo.presentation.type.NavigationSourceType
import sopt.motivoo.presentation.type.SoreSpotType
import sopt.motivoo.presentation.type.TimeType
import sopt.motivoo.presentation.type.UserType
import sopt.motivoo.presentation.type.WhatActivityType
import sopt.motivoo.presentation.type.WhatExerciseType
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _userType = MutableStateFlow<UserType?>(null)
    val userType get() = _userType.asStateFlow()

    val age = MutableStateFlow<String?>(null)

    val isValidAge: StateFlow<Boolean?> = age.map { ageString ->
        when {
            ageString.isNullOrEmpty() -> null
            ageString.toIntOrNull()?.let { it in 14..99 } == true -> true
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val isValidNext: StateFlow<Boolean> =
        combine(
            userType,
            isValidAge,
        ) { userType, isValidAge ->
            userType != null && isValidAge == true
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _doExerciseType = MutableStateFlow<DoExerciseType?>(null)
    val doExerciseType get() = _doExerciseType.asStateFlow()

    private val _whatExerciseType = MutableStateFlow<WhatExerciseType?>(null)
    val whatExerciseType get() = _whatExerciseType.asStateFlow()

    private val _whatActivityType = MutableStateFlow<WhatActivityType?>(null)
    val whatActivityType get() = _whatActivityType.asStateFlow()

    private val _frequencyType = MutableStateFlow<FrequencyType?>(null)
    val frequencyType get() = _frequencyType.asStateFlow()

    private val _timeType = MutableStateFlow<TimeType?>(null)
    val timeType get() = _timeType.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<OnboardingNavigationEvent>()
    val navigationEvent get() = _navigationEvent.asSharedFlow()

    val soreSpotFilterType = MutableStateFlow(
        mapOf(
            SoreSpotType.NECK to false,
            SoreSpotType.SHOULDER to false,
            SoreSpotType.WAIST to false,
            SoreSpotType.KNEE to false,
            SoreSpotType.WRIST to false,
            SoreSpotType.ANKLE to false,
        )
    )

    private val _isPostOnboardingInfoSuccess = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val isPostOnboardingInfoSuccess get() = _isPostOnboardingInfoSuccess.asStateFlow()
    fun setDoExerciseType(doExerciseType: DoExerciseType) {
        _doExerciseType.value = doExerciseType
        viewModelScope.launch {
            if (doExerciseType == DoExerciseType.YES) {
                _navigationEvent.emit(OnboardingNavigationEvent.NavigateToForthPageExe)
            } else {
                _navigationEvent.emit(OnboardingNavigationEvent.NavigateToForthPageAct)
            }
        }
    }

    fun setWhatExerciseType(whatExerciseType: WhatExerciseType) {
        _whatExerciseType.value = whatExerciseType
        viewModelScope.launch {
            doExerciseType.value?.let {
                _navigationEvent.emit(
                    OnboardingNavigationEvent.NavigateToFifthPage(
                        it,
                        NavigationSourceType.FROM_EXERCISE
                    )
                )
            }
        }
    }

    fun setWhatActivityType(whatActivityType: WhatActivityType) {
        _whatActivityType.value = whatActivityType
        viewModelScope.launch {
            doExerciseType.value?.let {
                _navigationEvent.emit(
                    OnboardingNavigationEvent.NavigateToFifthPage(
                        it,
                        NavigationSourceType.FROM_ACTIVITY
                    )
                )
            }
        }
    }

    fun setFrequencyType(frequencyType: FrequencyType) {
        _frequencyType.value = frequencyType
        viewModelScope.launch {
            doExerciseType.value?.let {
                _navigationEvent.emit(OnboardingNavigationEvent.NavigateToSixthPage(it))
            }
        }
    }

    fun setTimeType(timeType: TimeType) {
        _timeType.value = timeType
        viewModelScope.launch {
            _navigationEvent.emit(OnboardingNavigationEvent.NavigateToLastPage)
        }
    }

    fun setSoreSpotFilterType(soreSpotType: SoreSpotType) {
        val isSelected = soreSpotFilterType.value[soreSpotType] ?: return
        val selectedCount = soreSpotFilterType.value.count { it.value }

        if (selectedCount >= 3 && !isSelected) return

        soreSpotFilterType.value = soreSpotFilterType.value.toMutableMap().apply {
            this[soreSpotType] = !isSelected
        }
    }

    fun setUserType(userType: UserType) {
        _userType.value = userType
    }

    fun postOnboardingInfo(
        userTypeString: String,
        whatExerciseTypeString: String,
        whatActivityTypeString: String,
        frequencyTypeString: String,
        timeTypeString: String,
        isDoExercise: Boolean,
        selectedSoreSpotString: List<String>
    ) {
        viewModelScope.launch {
            val exerciseType =
                if (isDoExercise) whatExerciseTypeString else whatActivityTypeString
            val requestDto = RequestOnboardingDto(
                age = age.value?.toIntOrNull() ?: 0,
                exerciseCount = frequencyTypeString,
                exerciseNote = selectedSoreSpotString,
                exerciseTime = timeTypeString,
                exerciseType = exerciseType,
                isExercise = isDoExercise,
                type = userTypeString
            )
            onboardingRepository.postOnboardingInfo(requestDto)
                .onSuccess {
                    motivooStorage.isFinishedOnboarding = true
                    _isPostOnboardingInfoSuccess.value = UiState.Success(true)
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }
}

sealed class OnboardingNavigationEvent {
    data object NavigateToForthPageExe : OnboardingNavigationEvent()

    data object NavigateToForthPageAct : OnboardingNavigationEvent()

    data class NavigateToFifthPage(
        val doExerciseType: DoExerciseType,
        val navigationSourceType: NavigationSourceType
    ) : OnboardingNavigationEvent()

    data class NavigateToSixthPage(val doExerciseType: DoExerciseType) :
        OnboardingNavigationEvent()

    data object NavigateToLastPage : OnboardingNavigationEvent()
}
