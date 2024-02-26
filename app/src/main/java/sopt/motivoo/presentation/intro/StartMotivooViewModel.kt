package sopt.motivoo.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StartMotivooViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _isOnboardingFinished = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val isOnboardingFinished get() = _isOnboardingFinished.asStateFlow()

    fun getOnboardingFinished() {
        viewModelScope.launch {
            onboardingRepository.getOnboardingFinished()
                .onSuccess { isOnboardingFinished ->
                    motivooStorage.isFinishedOnboarding = isOnboardingFinished.isFinishedOnboarding
                    _isOnboardingFinished.value = UiState.Success(true)
                }.onFailure {
                    Timber.e(it.message)
                    _isOnboardingFinished.value = UiState.Failure(it.toString())
                }
        }
    }

    fun resetStartState() {
        _isOnboardingFinished.value = UiState.Loading
    }
}
