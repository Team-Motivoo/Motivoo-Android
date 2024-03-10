package sopt.motivoo.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.OnboardingRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    fun getOnboardingFinished() {
        viewModelScope.launch {
            onboardingRepository.getOnboardingFinished()
                .onSuccess {
                    motivooStorage.isFinishedOnboarding = it.data.isFinishedOnboarding
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }
}
