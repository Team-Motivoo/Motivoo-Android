package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentWhatExerciseQusetionBinding
import sopt.motivoo.util.binding.BindingFragment

class WhatExerciseQuestionFragment :
    BindingFragment<FragmentWhatExerciseQusetionBinding>(R.layout.fragment_what_exercise_qusetion) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
    }
}
