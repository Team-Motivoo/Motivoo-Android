package sopt.motivoo.presentation.auth

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.data.service.KakaoAuthService
import sopt.motivoo.databinding.FragmentLoginBinding
import sopt.motivoo.presentation.invitecode.GetInviteCodeFragment
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible
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
                    authViewModel.resetLoginState()
                    findNavController().navigate(R.id.action_loginFragment_to_termsOfUseFragment)
                }

                is UiState.Failure -> {
                    showLoginErrorMessage()
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun showLoginErrorMessage() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvLoginErrorMessage.startAnimation(fadeIn)
        binding.tvLoginErrorMessage.setVisible(VISIBLE)

        lifecycleScope.launch {
            delay(GetInviteCodeFragment.TWO_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvLoginErrorMessage.startAnimation(fadeOut)
            binding.tvLoginErrorMessage.setVisible(GONE)
        }
    }
}
