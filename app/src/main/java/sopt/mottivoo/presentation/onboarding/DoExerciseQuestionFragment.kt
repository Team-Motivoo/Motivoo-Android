package sopt.mottivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import sopt.mottivoo.R
import sopt.mottivoo.databinding.FragmentDoExerciseQuestionBinding
import sopt.mottivoo.util.binding.BindingFragment

class DoExerciseQuestionFragment :
    BindingFragment<FragmentDoExerciseQuestionBinding>(R.layout.fragment_do_exercise_question) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
    }
}
