package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentAgeQusetionBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.drawableOf
import sopt.motivoo.util.extension.px
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible
import sopt.motivoo.util.extension.showKeyboard

class AgeQuestionFragment :
    BindingFragment<FragmentAgeQusetionBinding>(R.layout.fragment_age_qusetion) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()
    private var isAnimationPlayed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel

        collectData()
        clickNextButton()
        overrideOnBackPressed()
    }

    private fun clickNextButton() {
        binding.btnAgeQuestionDone.setOnSingleClickListener {
            findNavController().navigate(R.id.action_ageQuestionFragment_to_doExerciseQuestionFragment)
        }
    }

    private fun collectData() {
        onboardingViewModel.userType.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { userType ->
                if (userType != null) {
                    showAgeLayout(
                        binding.clAgeQuestion,
                        getString(R.string.age_question_title_second),
                        getString(R.string.age_question_description_second)
                    )
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        onboardingViewModel.isValidAge.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { isValidAge ->
                when (isValidAge) {
                    null, true -> {
                        binding.etAgeQuestion.background =
                            requireContext().drawableOf(R.drawable.selector_edittext_input)
                        binding.tvAgeQuestionErrorMessage.setVisible(GONE)
                    }

                    false -> {
                        binding.etAgeQuestion.background =
                            requireContext().drawableOf(R.drawable.shape_edittext_error_radius8)
                        binding.tvAgeQuestionErrorMessage.setVisible(VISIBLE)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        onboardingViewModel.isValidNext.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { isValidNext ->
                binding.btnAgeQuestionDone.isEnabled = isValidNext
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showAgeLayout(view: View, newTitle: String, newDescription: String) {
        if (!isAnimationPlayed) {
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
            binding.etAgeQuestion.requestFocus()
            requireContext().showKeyboard(binding.etAgeQuestion)
            isAnimationPlayed = true
        }
        view.setVisible(VISIBLE)
        changeTopMarginAfterAnimation()
        changeText(newTitle, newDescription)
    }

    private fun changeText(newTitle: String, newDescription: String) {
        binding.tvAgeQuestionTitle.text = newTitle
        binding.tvAgeQuestionDescription.text = newDescription
    }

    private fun changeTopMarginAfterAnimation() {
        val layoutParams = binding.clUserType.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = TOP_MARGIN.px.toInt()
        binding.clUserType.layoutParams = layoutParams
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
        private const val TOP_MARGIN = 13
    }
}
