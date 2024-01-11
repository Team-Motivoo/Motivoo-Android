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

class MyServiceOut : DialogFragment() {

    private lateinit var binding: FragmentMypageServiceOutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMypageServiceOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
