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
import sopt.motivoo.databinding.FragmentWhatExerciseQusetionBinding
import sopt.motivoo.util.binding.BindingFragment

class WhatExerciseQuestionFragment :
    BindingFragment<FragmentWhatExerciseQusetionBinding>(R.layout.fragment_what_exercise_qusetion) {

    private val whatExerciseQuestionViewModel by activityViewModels<OnboardingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = whatExerciseQuestionViewModel
        collectData()
    }

    private fun collectData() {
        whatExerciseQuestionViewModel.navigateToFifthPageExe.flowWithLifecycle(lifecycle)
            .onEach {
                if (findNavController().currentDestination?.id == R.id.whatExerciseQuestionFragment) {
                    findNavController().navigate(R.id.action_whatExerciseQuestionFragment_to_frequencyQuestionFragment)
                }
            }.launchIn(lifecycleScope)
    }
}
