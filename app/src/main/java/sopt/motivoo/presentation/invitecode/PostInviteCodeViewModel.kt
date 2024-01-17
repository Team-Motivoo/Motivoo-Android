package sopt.motivoo.presentation.invitecode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.OnboardingRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostInviteCodeViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    val postInviteCode = MutableStateFlow("")

    val isPostInviteCodeEmpty: StateFlow<Boolean> = postInviteCode
        .map { it.isEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _postInviteCodeState = MutableStateFlow<OnboardingState>(OnboardingState.Init)
    val postInviteCodeState get() = _postInviteCodeState.asStateFlow()

    fun postInviteCode() {
        viewModelScope.launch {
            resetOnboardingState()
            onboardingRepository.patchInviteCode(
                RequestPostInviteCodeDto(
                    postInviteCode.value
                )
            )
                .onSuccess { isMatchedState ->
                    if (!isMatchedState.isMyInviteCode && isMatchedState.isMatched) {
                        motivooStorage.isUserMatched = true
                        _postInviteCodeState.value = if (isMatchedState.isFinishedOnboarding) {
                            OnboardingState.PassOnboarding
                        } else {
                            OnboardingState.GoToOnboarding
                        }
                    } else {
                        _postInviteCodeState.value =
                            OnboardingState.SameWithMyInviteCode
                    }
                }.onFailure {
                    Timber.e(it.message)
                    _postInviteCodeState.value = OnboardingState.Failure(it.toString())
                }
        }
    }

    fun resetOnboardingState() {
        _postInviteCodeState.value = OnboardingState.Init
    }

    sealed class OnboardingState {
        data object Init : OnboardingState()
        data object GoToOnboarding : OnboardingState()
        data class Failure(val message: String) : OnboardingState()
        data object PassOnboarding : OnboardingState()

        data object SameWithMyInviteCode : OnboardingState()
    }
}
