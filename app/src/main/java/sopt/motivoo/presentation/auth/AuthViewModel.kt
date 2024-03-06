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
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.presentation.type.SocialType
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val authRepository: AuthRepository,
    private val networkRepository: NetworkRepository,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val loginState get() = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutState get() = _logoutState.asStateFlow()

    private val _withDrawState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val withDrawState get() = _withDrawState.asStateFlow()

    fun resetLoginState() {
        _loginState.value = UiState.Loading
    }

    fun resetLogoutState() {
        _logoutState.value = UiState.Loading
    }

    fun resetWithDrawState() {
        _withDrawState.value = UiState.Loading
    }

    fun postLogin(
        platformToken: String,
    ) {
        viewModelScope.launch {
            networkRepository.setLoading(true)
            authRepository.postLogin(
                RequestLoginDto(
                    platformToken,
                    SocialType.kakao.name,
                ),
            ).onSuccess { signUpResponse ->
                networkRepository.setLoading(false)
                handleLoginSuccess(signUpResponse)
            }.onFailure { throwable ->
                networkRepository.setLoading(false)
                Timber.e(throwable.message)
            }
        }
    }

    private fun handleLoginSuccess(signUpResponse: LoginInfo) {
        motivooStorage.userId = signUpResponse.id
        motivooStorage.accessToken = BEARER_PREFIX + signUpResponse.accessToken
        motivooStorage.refreshToken = BEARER_PREFIX + signUpResponse.refreshToken
        _loginState.value = UiState.Success(true)
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

    suspend fun getOnboardingFinished() {
        onboardingRepository.getOnboardingFinished()
            .onSuccess {
                motivooStorage.isFinishedOnboarding = it.data.isFinishedOnboarding
            }
            .onFailure {
                Timber.e(it.message)
            }
    }

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }
}
