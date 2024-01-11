package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageLogoutBinding
import sopt.motivoo.util.binding.BindingDialogFragment

class MyLogout :
    BindingDialogFragment<FragmentMypageLogoutBinding>(R.layout.fragment_mypage_logout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
    }

    private fun clickButton() {
        binding.tvMyLogoutBtn.setOnClickListener {
            navigateToLogin()
        }

        binding.tvMyLogoutCancelBtn.setOnClickListener {
            navigateMyInfo()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_myLogout_to_loginFragment)
    }

    private fun navigateMyInfo() {
        findNavController().navigate(R.id.action_myLogout_to_myInfoFragment)
    }
}
