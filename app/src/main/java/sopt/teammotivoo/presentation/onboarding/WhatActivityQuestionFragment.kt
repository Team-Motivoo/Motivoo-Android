package sopt.teammotivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.FragmentWhatActivityQuestionBinding
import sopt.teammotivoo.util.binding.BindingFragment

class WhatActivityQuestionFragment :
    BindingFragment<FragmentWhatActivityQuestionBinding>(R.layout.fragment_what_activity_question) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel
    }
}
