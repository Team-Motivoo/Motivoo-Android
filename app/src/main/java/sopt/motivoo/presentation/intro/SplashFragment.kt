package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentSplashBinding
import sopt.motivoo.util.NavigationEvent
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.findStartDestination

@AndroidEntryPoint
class SplashFragment : BindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSplash()
    }

    private fun showSplash() {
        lifecycleScope.launch {
            delay(SPLASH_DISPLAY_LENGTH)
            collectData()
        }
    }

    private fun collectData() {
        splashViewModel.navigationEvent.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { event ->
                when (event) {
                    NavigationEvent.Home -> navigateToFragment(R.id.action_splashFragment_to_homeFragment)
                    NavigationEvent.AgeQuestion -> navigateToFragment(R.id.action_splashFragment_to_ageQuestionFragment)
                    NavigationEvent.StartMotivoo -> navigateToFragment(R.id.action_splashFragment_to_startMotivooFragment)
                    NavigationEvent.Login -> navigateToFragment(R.id.action_splashFragment_to_loginFragment)
                    NavigationEvent.TermsOfUse -> navigateToFragment(R.id.action_splashFragment_to_termsOfUseFragment)
                    NavigationEvent.Permission -> navigateToFragment(R.id.action_splashFragment_to_permissionFragment)
                    NavigationEvent.Init -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToFragment(destinationId: Int) {
        val navController = findNavController()
        val startDestinationId = navController.findStartDestination().id
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestinationId, true)
            .build()

        navController.navigate(destinationId, null, navOptions)
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 2000L
    }
}
