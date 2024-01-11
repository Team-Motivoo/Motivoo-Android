package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageServiceOutBinding
import sopt.motivoo.util.binding.BindingDialogFragment

class MyServiceOut :
    BindingDialogFragment<FragmentMypageServiceOutBinding>(R.layout.fragment_mypage_service_out) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
    }

    private fun clickButton() {
        binding.tvMyServiceOutBtn.setOnClickListener {
            navigateToLogin()
        }

        binding.tvMyServiceOutCancelBtn.setOnClickListener {
            navigateMyInfo()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_myServiceOut_to_loginFragment)
    }

    private fun navigateMyInfo() {
        findNavController().navigate(R.id.action_myServiceOut_to_myInfoFragment)
    }
}
