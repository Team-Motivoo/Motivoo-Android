package sopt.motivoo.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.AuthRepository
import sopt.motivoo.presentation.type.SocialType
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val loginState get() = _loginState.asStateFlow()

    private fun setAutoLogin() {
        motivooStorage.isLogin = true
    }

    fun resetLoginState() {
        _loginState.value = UiState.Loading
    }

    fun postLogin(
        platformToken: String,
    ) {
        viewModelScope.launch {
            Timber.tag("aaa").e("보냄")
            authRepository.postLogin(
                RequestLoginDto(
                    platformToken,
                    SocialType.kakao.name,
                ),
            ).onSuccess { signUpResponse ->
                motivooStorage.userId = signUpResponse.id
                motivooStorage.nickName = signUpResponse.nickName
                motivooStorage.accessToken = BEARER_PREFIX + signUpResponse.accessToken
                motivooStorage.refreshToken = BEARER_PREFIX + signUpResponse.refreshToken
                _loginState.value = UiState.Success(true)
                setAutoLogin()
                Timber.tag("aaa").e("성공")
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }
}
