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
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostInviteCodeViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
    private val networkRepository: NetworkRepository,
) : ViewModel() {

    val postInviteCode = MutableStateFlow("")

    val isPostInviteCodeEmpty: StateFlow<Boolean> = postInviteCode
        .map { it.isEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _postInviteCodeState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val postInviteCodeState get() = _postInviteCodeState.asStateFlow()

    fun postInviteCode() {
        viewModelScope.launch {
            resetOnboardingState()
            networkRepository.setLoading(true)
            onboardingRepository.patchInviteCode(
                RequestPostInviteCodeDto(
                    postInviteCode.value
                )
            ).onSuccess {
                motivooStorage.isUserMatched = it.isMatched
                _postInviteCodeState.value = UiState.Success(true)
                networkRepository.setLoading(false)
            }.onFailure {
                Timber.e(it.message)
                _postInviteCodeState.value = UiState.Failure(it.toString())
                networkRepository.setLoading(false)
            }
        }
    }

    fun resetOnboardingState() {
        _postInviteCodeState.value = UiState.Loading
    }
}
