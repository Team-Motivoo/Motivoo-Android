package sopt.motivoo.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageLogoutBinding
import sopt.motivoo.databinding.FragmentMypageServiceOutBinding
import sopt.motivoo.util.binding.BindingFragment

class MyLogout : BindingFragment<FragmentMypageLogoutBinding>(R.layout.fragment_mypage_logout){

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
        findNavController().navigate(R.id.action_myServiceOut_to_loginFragment)
    }

    private fun navigateMyInfo() {
        findNavController().navigate(R.id.action_myServiceOut_to_myInfoFragment)
    }
}