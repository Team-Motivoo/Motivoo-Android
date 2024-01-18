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
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.findStartDestination
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    @Inject
    lateinit var motivooStorage: MotivooStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSplash()
    }

    private fun showSplash() {
        lifecycleScope.launch {
            delay(2000)
            navigateToNextFragment()
        }
    }

    private fun navigateToNextFragment() {
        val navController = findNavController()
        val startDestinationId = navController.findStartDestination().id
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestinationId, true)
            .build()

        navController.navigate(R.id.action_splashFragment_to_permissionFragment, null, navOptions)
    }
}
