package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentWhatActivityQuestionBinding
import sopt.motivoo.util.binding.BindingFragment

class WhatActivityQuestionFragment :
    BindingFragment<FragmentWhatActivityQuestionBinding>(R.layout.fragment_what_activity_question) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
    }
}
