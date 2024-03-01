package sopt.motivoo.presentation

import android.content.Intent
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
import sopt.motivoo.databinding.FragmentMypageServiceOutBinding
import sopt.motivoo.presentation.MainActivity.Companion.REDIRECT_TO_LOGIN
import sopt.motivoo.presentation.auth.AuthViewModel
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingDialogFragment
import javax.inject.Inject

@AndroidEntryPoint
class MyServiceOutFragment :
    BindingDialogFragment<FragmentMypageServiceOutBinding>(R.layout.fragment_mypage_service_out) {

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 0.91f, heightPercent = 0.4f)
        clickButtons()
    }

    private fun clickButtons() {
        binding.tvMyServiceOutBtn.setOnClickListener {
            kakaoAuthService.withdrawKakao(authViewModel::withDraw)
            collectData()
        }

        binding.tvMyServiceOutCancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun collectData() {
        authViewModel.withDrawState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        authViewModel.resetWithDrawState()
                        navigateToLogin()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(REDIRECT_TO_LOGIN, "new_activity")
        startActivity(intent)
    }
}
