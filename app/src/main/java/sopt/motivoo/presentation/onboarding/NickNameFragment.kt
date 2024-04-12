package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentNicknameBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener

class NickNameFragment : BindingFragment<FragmentNicknameBinding>(R.layout.fragment_nickname) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel

        collectData()
        clickNextButton()
        overrideOnBackPressed()
    }

    private fun clickNextButton() {
        binding.btnNicknameDone.setOnSingleClickListener {
            findNavController().navigate(R.id.action_nickNameFragment_to_ageQuestionFragment)
        }
    }

    private fun collectData() {
        onboardingViewModel.nickName.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { nickName ->
                binding.btnNicknameDone.isEnabled = !nickName.isNullOrEmpty()
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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
}