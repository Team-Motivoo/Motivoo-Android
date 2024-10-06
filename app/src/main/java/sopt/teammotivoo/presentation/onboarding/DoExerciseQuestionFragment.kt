package sopt.teammotivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.FragmentDoExerciseQuestionBinding
import sopt.teammotivoo.util.binding.BindingFragment

class DoExerciseQuestionFragment :
    BindingFragment<FragmentDoExerciseQuestionBinding>(R.layout.fragment_do_exercise_question) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
    }
}
