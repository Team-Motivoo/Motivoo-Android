package sopt.motivoo.presentation.mypage

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
import sopt.motivoo.presentation.auth.AuthViewModel
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.redirectToLogin
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
                        requireContext().redirectToLogin()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
