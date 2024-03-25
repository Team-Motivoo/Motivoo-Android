package sopt.motivoo.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.motivoo.data.model.request.auth.RequestLoginDto
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.entity.auth.LoginInfo
import sopt.motivoo.domain.repository.AuthRepository
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.domain.repository.UserRepository
import sopt.motivoo.presentation.type.SocialType
import sopt.motivoo.util.NavigationDecider
import sopt.motivoo.util.NavigationEvent
import sopt.motivoo.util.UiState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val authRepository: AuthRepository,
    private val stepCountRepository: StepCountRepository,
    private val userRepository: UserRepository,
    private val navigationDecider: NavigationDecider
) : ViewModel() {

    private val _navigationEvent =
        MutableStateFlow<NavigationEvent>(NavigationEvent.Init)
    val navigationEvent get() = _navigationEvent.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val loginState get() = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val logoutState get() = _logoutState.asStateFlow()

    private val _withDrawState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val withDrawState get() = _withDrawState.asStateFlow()

    fun postLogin(
        platformToken: String,
    ) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            authRepository.postLogin(
                RequestLoginDto(
                    platformToken,
                    SocialType.kakao.name,
                ),
            ).onSuccess { signUpResponse ->
                handleLoginSuccess(signUpResponse)
            }.onFailure { throwable ->
                _loginState.value = UiState.Failure(throwable.message.toString())
            }
        }
    }

    private fun handleLoginSuccess(signUpResponse: LoginInfo) {
        motivooStorage.userId = signUpResponse.id
        motivooStorage.accessToken = BEARER_PREFIX + signUpResponse.accessToken
        motivooStorage.refreshToken = BEARER_PREFIX + signUpResponse.refreshToken
        motivooStorage.isUserMatched = signUpResponse.isMatched
        motivooStorage.isFinishedOnboarding = signUpResponse.isOnboardingFinished
        motivooStorage.isUserLoggedIn = true
        _loginState.value = UiState.Success(true)
    }

    fun checkNavigateState() {
        _navigationEvent.value = navigationDecider.determineNavigationDestination()
    }

    fun postLogout() {
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess {
                    _logoutState.value = UiState.Success(true)
                    motivooStorage.logout()
                }.onFailure { throwable ->
                    _logoutState.value = UiState.Failure(throwable.message.toString())
                }
        }
    }

    fun withDraw() {
        viewModelScope.launch {
            authRepository.deleteWithDraw()
                .onSuccess {
                    _withDrawState.value = UiState.Success(true)
                    motivooStorage.clear()
                }.onFailure { throwable ->
                    _withDrawState.value = UiState.Failure(throwable.message.toString())
                }
        }
    }

    fun clearLocalDataStore() {
        viewModelScope.launch {
            stepCountRepository.clearMyStepCount()
            userRepository.clearUserId()
        }
    }

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }
}
