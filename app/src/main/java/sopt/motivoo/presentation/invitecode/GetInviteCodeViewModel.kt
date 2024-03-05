package sopt.motivoo.presentation.invitecode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetInviteCodeViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _inviteCode = MutableStateFlow<String?>(null)
    val inviteCode get() = _inviteCode.asStateFlow()

    private val _checkMatchState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val checkMatchState get() = _checkMatchState.asStateFlow()

    init {
        getInviteCode()
    }

    private fun getInviteCode() {
        viewModelScope.launch {
            onboardingRepository.getInviteCode()
                .onSuccess {
                    _inviteCode.value = it.inviteCode
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    fun getMatchedResult() {
        viewModelScope.launch {
            _checkMatchState.value = UiState.Loading
            onboardingRepository.getMatchedResult()
                .onSuccess {
                    if (it.isMatched) {
                        motivooStorage.isUserMatched = true
                        _checkMatchState.value = UiState.Success(true)
                    }
                }.onFailure {
                    Timber.e(it.message)
                    _checkMatchState.value = UiState.Failure(it.toString())
                }
        }
    }
}
