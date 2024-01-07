package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentFrequencyQusetionBinding
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.binding.BindingFragment

class FrequencyQuestionFragment :
    BindingFragment<FragmentFrequencyQusetionBinding>(R.layout.fragment_frequency_qusetion) {

    private val frequencyQuestionViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = frequencyQuestionViewModel
        collectData()
    }

    private fun collectData() {
        frequencyQuestionViewModel.doExerciseType.flowWithLifecycle(lifecycle)
            .onEach { doExercise ->
                when (doExercise) {
                    DoExerciseType.YES ->
                        binding.tvFrequencyTitle.text = getText(R.string.frequency_exercise_title)

                    DoExerciseType.NO ->
                        binding.tvFrequencyTitle.text = getText(R.string.frequency_activity_title)

                    else -> Unit
                }
            }.launchIn(lifecycleScope)

        frequencyQuestionViewModel.navigateToSixthPage.flowWithLifecycle(lifecycle).onEach {
            if (findNavController().currentDestination?.id == R.id.frequencyQuestionFragment) {
                findNavController().navigate(R.id.action_frequencyQuestionFragment_to_timeQuestionFragment)
            }
        }.launchIn(lifecycleScope)
    }
}
