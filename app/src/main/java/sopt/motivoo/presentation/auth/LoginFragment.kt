package sopt.motivoo.presentation.auth

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.data.service.KakaoAuthService
import sopt.motivoo.databinding.FragmentLoginBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.presentation.invitecode.GetInviteCodeFragment
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    @Inject
    lateinit var motivooStorage: MotivooStorage
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickKakaoLoginButton()
        collectData()
        overrideOnBackPressed()
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun goToStartMotivoo() {
        findNavController().navigate(R.id.action_loginFragment_to_startMotivooFragment)
    }

    private fun clickKakaoLoginButton() {
        binding.llKakaoLogin.setOnSingleClickListener {
            kakaoAuthService.startKakaoLogin { token ->
                authViewModel.postLogin(token)
            }
        }
    }

    private fun collectData() {
        authViewModel.loginState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        authViewModel.resetLoginState()
                        viewLifecycleOwner.lifecycleScope.launch {
                            authViewModel.getOnboardingFinished()
                            if (motivooStorage.isUserMatched) {
                                goToHome()
                            } else if (!motivooStorage.isUserMatched && motivooStorage.isFinishedOnboarding) {
                                Timber.tag("bbb")
                                    .e("${motivooStorage.isUserMatched}, ${motivooStorage.isUserLoggedIn}, ${motivooStorage.isFinishedOnboarding}")
                                goToStartMotivoo()
                            } else {
                                findNavController().navigate(R.id.action_loginFragment_to_ageQuestionFragment)
                            }
                        }
                    }

                    is UiState.Failure -> {
                        showLoginErrorMessage()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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
