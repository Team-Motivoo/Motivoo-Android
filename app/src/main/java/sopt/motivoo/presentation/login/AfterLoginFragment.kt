package sopt.motivoo.presentation.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentAfterLoginBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener

class AfterLoginFragment :
    BindingFragment<FragmentAfterLoginBinding>(R.layout.fragment_after_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButton()
    }

    private fun clickButton() {
        binding.btnStartMotivoo.setOnSingleClickListener {
            findNavController().navigate(R.id.action_afterLoginFragment_to_ageQuestionFragment)
        }

        binding.btnPostInviteCode.setOnSingleClickListener {
            findNavController().navigate(R.id.action_afterLoginFragment_to_postInviteCodeFragment)
        }
    }
}
