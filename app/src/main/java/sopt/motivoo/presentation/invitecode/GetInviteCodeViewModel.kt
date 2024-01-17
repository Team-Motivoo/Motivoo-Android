package sopt.motivoo.presentation.invitecode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetInviteCodeViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _checkMatchState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val checkMatchState get() = _checkMatchState.asStateFlow()

    fun getInviteCode() {
        viewModelScope.launch {
            _checkMatchState.value = UiState.Loading
            onboardingRepository.getInviteCode()
                .onSuccess {
                    if (it.isMatched) {
                        _checkMatchState.value = UiState.Success(true)
                    }
                }.onFailure {
                    Timber.e(it.message)
                    _checkMatchState.value = UiState.Failure(it.toString())
                }
        }
    }
}
