package sopt.mottivoo.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.mottivoo.R
import sopt.mottivoo.data.service.KakaoAuthService
import sopt.mottivoo.databinding.FragmentMypageLogoutBinding
import sopt.mottivoo.presentation.auth.AuthViewModel
import sopt.mottivoo.util.UiState
import sopt.mottivoo.util.binding.BindingDialogFragment
import sopt.mottivoo.util.extension.redirectToLogin
import sopt.mottivoo.util.extension.setOnSingleClickListener
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
        authViewModel.logoutState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        requireContext().redirectToLogin()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
