package sopt.motivoo.presentation.withdrawal

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentWithdrawalBinding
import sopt.motivoo.util.binding.BindingFragment

class WithdrawalFragment :
    BindingFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
        backPressed()
    }

    private fun clickButton() {
        binding.btnWithdrawal.setOnClickListener {
            navigateToServiceOut()
        }
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }

    private fun navigateToServiceOut() {
        findNavController().navigate(R.id.action_withdrawalFragment_to_myServiceOutFragment)
    }
}
