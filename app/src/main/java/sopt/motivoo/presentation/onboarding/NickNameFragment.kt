package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentNicknameBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.drawableOf
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

                if (nickName.toString().length >= 8) {
                    setErrorAnimation()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setErrorAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvNicknameErrorMessage.startAnimation(fadeIn)
        binding.etNickname.background =
            requireContext().drawableOf(R.drawable.shape_edittext_error_radius8)
        binding.tvNicknameErrorMessage.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(FOUR_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvNicknameErrorMessage.startAnimation(fadeOut)
            binding.tvNicknameErrorMessage.visibility = View.INVISIBLE
            binding.etNickname.background =
                requireContext().drawableOf(R.drawable.selector_edittext_input)
        }
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

    companion object {
        const val FOUR_SECONDS = 4000L
    }
}