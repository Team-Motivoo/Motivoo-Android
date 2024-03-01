package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentSplashBinding
import sopt.motivoo.presentation.MainActivity.Companion.REDIRECT_TO_LOGIN
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.findStartDestination

@AndroidEntryPoint
class SplashFragment : BindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAndNavigate()
    }

    private fun checkAndNavigate() {
        val redirectToLogin = arguments?.getBoolean(REDIRECT_TO_LOGIN) ?: false
        if (redirectToLogin) {
            navigateToFragment(R.id.action_splashFragment_to_loginFragment)
        } else {
            showSplash()
        }
    }

    private fun showSplash() {
        lifecycleScope.launch {
            delay(2000)
            navigateToFragment(R.id.action_splashFragment_to_permissionFragment)
        }
    }

    private fun navigateToFragment(destinationId: Int) {
        val navController = findNavController()
        val startDestinationId = navController.findStartDestination().id
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestinationId, true)
            .build()

        navController.navigate(destinationId, null, navOptions)
    }
}
