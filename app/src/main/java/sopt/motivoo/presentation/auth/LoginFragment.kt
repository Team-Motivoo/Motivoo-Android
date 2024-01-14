package sopt.motivoo.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.data.service.KakaoAuthService
import sopt.motivoo.databinding.FragmentLoginBinding
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.showToast
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickKakaoLoginButton()
        collectData()
    }

    private fun clickKakaoLoginButton() {
        binding.llKakaoLogin.setOnSingleClickListener {
            kakaoAuthService.startKakaoLogin { token ->
                authViewModel.postLogin(token)
            }
        }
    }

    private fun collectData() {
        authViewModel.loginState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    requireContext().showToast("로그인에 성공했어요.")
                    findNavController().navigate(R.id.action_loginFragment_to_termsOfUseFragment)
                    authViewModel.resetLoginState()
                }

                is UiState.Failure -> {
                    requireContext().showToast("로그인에 실패했어요.")
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}
