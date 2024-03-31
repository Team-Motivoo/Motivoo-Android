package sopt.mottivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import sopt.mottivoo.R
import sopt.mottivoo.databinding.FragmentConnectionErrorBinding
import sopt.mottivoo.util.binding.BindingFragment
import sopt.mottivoo.util.extension.setOnSingleClickListener

class NetworkErrorFragment :
    BindingFragment<FragmentConnectionErrorBinding>(R.layout.fragment_connection_error) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overrideOnBackPressed()
        clickRetry()
    }

    private fun clickRetry() {
        binding.tvConnectionErrorBtn.setOnSingleClickListener {
            findNavController().popBackStack()
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
