package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentTimeQusetionBinding
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.binding.BindingFragment

class TimeQuestionFragment :
    BindingFragment<FragmentTimeQusetionBinding>(R.layout.fragment_time_qusetion) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
        collectData()
    }

    private fun collectData() {
        onboardingViewModel.doExerciseType.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { doExercise ->
                when (doExercise) {
                    DoExerciseType.YES ->
                        binding.tvTimeTitle.text = getText(R.string.time_exercise_title)

                    DoExerciseType.NO ->
                        binding.tvTimeTitle.text = getText(R.string.time_activity_title)

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        onboardingViewModel.navigateToLastPage.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach {
                if (findNavController().currentDestination?.id == R.id.timeQuestionFragment) {
                    findNavController().navigate(R.id.action_timeQuestionFragment_to_soreSpotQuestionFragment)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
