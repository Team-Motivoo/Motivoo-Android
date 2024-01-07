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
import sopt.motivoo.databinding.FragmentWhatActivityQuestionBinding
import sopt.motivoo.util.binding.BindingFragment

class WhatActivityQuestionFragment :
    BindingFragment<FragmentWhatActivityQuestionBinding>(R.layout.fragment_what_activity_question) {

    private val whatActivityQuestionViewModel by activityViewModels<OnboardingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = whatActivityQuestionViewModel
        collectData()
    }

    private fun collectData() {
        whatActivityQuestionViewModel.navigateToFifthPageAct.flowWithLifecycle(lifecycle)
            .onEach {
                if (findNavController().currentDestination?.id == R.id.whatActivityQuestionFragment) {
                    findNavController().navigate(R.id.action_whatActivityQuestionFragment_to_frequencyExerciseQuestionFragment)
                }
            }.launchIn(lifecycleScope)
    }
}