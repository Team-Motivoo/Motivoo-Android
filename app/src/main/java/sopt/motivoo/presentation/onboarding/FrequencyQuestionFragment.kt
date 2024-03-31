package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentFrequencyQusetionBinding
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.binding.BindingFragment

class FrequencyQuestionFragment :
    BindingFragment<FragmentFrequencyQusetionBinding>(R.layout.fragment_frequency_qusetion) {

    private val args: FrequencyQuestionFragmentArgs by navArgs()

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
        updateUi()
    }

    private fun updateUi() {
        binding.tvFrequencyTitle.text = when (args.doExerciseType) {
            DoExerciseType.YES -> getText(R.string.frequency_exercise_title)
            DoExerciseType.NO -> getText(R.string.frequency_activity_title)
        }
    }
}
