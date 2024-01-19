package sopt.motivoo.presentation

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
import sopt.motivoo.databinding.FragmentMypageLogoutBinding
import sopt.motivoo.presentation.auth.AuthViewModel
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import javax.inject.Inject

@AndroidEntryPoint
class MyLogoutFragment :
    BindingDialogFragment<FragmentMypageLogoutBinding>(R.layout.fragment_mypage_logout) {

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 0.91f, heightPercent = 0.4f)
        clickButtons()
    }

    private fun clickButtons() {
        binding.tvMyLogoutBtn.setOnSingleClickListener {
            kakaoAuthService.logoutKakao(authViewModel::postLogout)
            collectData()
        }

        binding.tvMyLogoutCancelBtn.setOnSingleClickListener {
            findNavController().popBackStack()
        }
    }

    private fun collectData() {
        authViewModel.logoutState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    authViewModel.resetLogoutState()
                    navigateToLogin()
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_myLogout_to_loginFragment)
    }
}
