package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.ActivityMainBinding
import sopt.motivoo.util.binding.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fc_main)?.findNavController()

        with(binding) {
            bnvMain.itemIconTintList = null
            navController?.let { navController ->
                bnvMain.setupWithNavController(navController)
            }
        }
        navController?.let { setBottomVisible(it) }
    }

    private fun setBottomVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvMain.visibility = if (destination.id in listOf(
                    R.id.splashFragment,
                    R.id.loginFragment,
                )
            ) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}
