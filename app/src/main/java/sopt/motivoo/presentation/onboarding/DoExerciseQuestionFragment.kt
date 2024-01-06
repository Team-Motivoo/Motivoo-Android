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
import sopt.motivoo.databinding.FragmentDoExerciseQuestionBinding
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.binding.BindingFragment

class DoExerciseQuestionFragment :
    BindingFragment<FragmentDoExerciseQuestionBinding>(R.layout.fragment_do_exercise_question) {

    private val doExerciseQuestionViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = doExerciseQuestionViewModel

        collectData()
    }

    private fun collectData() {
        doExerciseQuestionViewModel.navigateTo.flowWithLifecycle(lifecycle)
            .onEach { doExercise ->
                if (findNavController().currentDestination?.id == R.id.doExerciseQuestionFragment) {
                    when (doExercise) {
                        DoExerciseType.YES -> findNavController().navigate(R.id.action_doExerciseQuestionFragment_to_whatExerciseQuestionFragment)
                        DoExerciseType.NO -> findNavController().navigate(R.id.action_doExerciseQuestionFragment_to_whatActivityQuestionFragment)
                        else -> Unit
                    }
                }
            }.launchIn(lifecycleScope)
    }
}
