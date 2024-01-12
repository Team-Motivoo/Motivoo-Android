package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentStartMotivooBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener

class StartMotivooFragment :
    BindingFragment<FragmentStartMotivooBinding>(R.layout.fragment_start_motivoo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButton()
    }

    private fun clickButton() {
        binding.btnStartMotivoo.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_ageQuestionFragment)
        }

        binding.btnPostInviteCode.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_postInviteCodeFragment)
        }
    }
}
