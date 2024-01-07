package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentSplashBinding
import sopt.motivoo.util.binding.BindingFragment

class SplashFragment : BindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSplash()
    }

    private fun showSplash() {
        lifecycleScope.launch {
            delay(1500)
            navigateToPermission()
        }
    }

    private fun navigateToPermission() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true)
            .build()

        findNavController().navigate(
            R.id.action_splashFragment_to_permissionFragment,
            null,
            navOptions
        )
    }
}
