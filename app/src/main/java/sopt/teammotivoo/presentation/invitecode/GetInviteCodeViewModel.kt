package sopt.teammotivoo.presentation.invitecode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.teammotivoo.domain.entity.MotivooStorage
import sopt.teammotivoo.domain.repository.OnboardingRepository
import sopt.teammotivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetInviteCodeViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _inviteCode = MutableLiveData<String>()
    val inviteCode get() = _inviteCode

    private val _checkMatchState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
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
