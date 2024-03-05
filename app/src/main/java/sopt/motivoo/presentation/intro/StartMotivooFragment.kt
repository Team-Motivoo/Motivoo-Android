package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentStartMotivooBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class StartMotivooFragment :
    BindingFragment<FragmentStartMotivooBinding>(R.layout.fragment_start_motivoo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
        overrideOnBackPressed()
    }

    private fun clickButton() {
        binding.btnPostInviteCode.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_postInviteCodeFragment)
        }

        binding.btnStartMotivoo.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_getInviteCodeFragment)
        }
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }
}
