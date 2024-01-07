package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentSoreSpotQusetionBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener

class SoreSpotQuestionFragment :
    BindingFragment<FragmentSoreSpotQusetionBinding>(R.layout.fragment_sore_spot_qusetion) {

    private val soreSpotQuestionViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = soreSpotQuestionViewModel

        moveToNext()

    }

    // TODO 초대하는 사람, 초대 받는 사람 분기 처리
    private fun moveToNext() {
        binding.btnOnboardingDone.setOnSingleClickListener { findNavController().navigate(R.id.action_soreSpotQuestionFragment_to_getInviteCodeFragment) }
    }
}