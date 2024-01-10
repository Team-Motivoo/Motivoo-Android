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
import sopt.motivoo.databinding.FragmentMypageServiceOutBinding
import sopt.motivoo.util.binding.BindingFragment

class MyServiceOut : BindingFragment<FragmentMypageServiceOutBinding>(R.layout.fragment_mypage_service_out){

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