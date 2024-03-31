package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentTimeQusetionBinding
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.binding.BindingFragment

class TimeQuestionFragment :
    BindingFragment<FragmentTimeQusetionBinding>(R.layout.fragment_time_qusetion) {

    private val args: TimeQuestionFragmentArgs by navArgs()

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
        updateUi()
    }

    private fun updateUi() {
        binding.tvTimeTitle.text = when (args.doExerciseType) {
            DoExerciseType.YES -> getText(R.string.time_exercise_title)
            DoExerciseType.NO -> getText(R.string.time_activity_title)
        }
    }
}
