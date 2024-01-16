package sopt.motivoo.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListenerImpl
import sopt.motivoo.databinding.ActivityMainBinding
import sopt.motivoo.util.extension.hideKeyboard
import sopt.motivoo.util.extension.setOnSingleClickListener
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var authTokenRefreshListener: AuthTokenRefreshListenerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MOTIVOOAOS)
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupTokenRefreshListener()
    }

    private fun initView() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fc_main)?.findNavController()

        with(binding) {
            bnvMain.itemIconTintList = null
            navController?.let { navController ->
                bnvMain.setupWithNavController(navController)
                setTopVisible(navController)
            }
        }
        navController?.let { setBottomVisible(it) }

        binding.includeToolbar.tvToolbarBack.setOnSingleClickListener { navController?.popBackStack() }
    }

    private fun setBottomVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvMain.visibility = if (destination.id in listOf(
                    R.id.homeFragment,
                    R.id.myPageFragment,
                    R.id.exerciseFragment,
                    R.id.myInfoFragment,
                    R.id.myExerciseInfoFragment,
                    R.id.myServiceOutFragment,
                    R.id.myLogoutFragment
                )
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setTopVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.clOnboardingToolbar.visibility = if (destination.id in listOf(
                    R.id.ageQuestionFragment,
                    R.id.doExerciseQuestionFragment,
                    R.id.frequencyQuestionFragment,
                    R.id.soreSpotQuestionFragment,
                    R.id.timeQuestionFragment,
                    R.id.whatExerciseQuestionFragment,
                    R.id.whatActivityQuestionFragment,
                )
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

            val progressValue = getProgressValue(destination.id)
            binding.onboardingProgress.progress = progressValue
        }
    }

    private fun getProgressValue(destinationId: Int): Float {
        return when (destinationId) {
            R.id.ageQuestionFragment -> 1f
            R.id.doExerciseQuestionFragment -> 2f
            R.id.whatExerciseQuestionFragment -> 3f
            R.id.whatActivityQuestionFragment -> 3f
            R.id.frequencyQuestionFragment -> 4f
            R.id.timeQuestionFragment -> 5f
            R.id.soreSpotQuestionFragment -> 6f
            else -> 0f
        }
    }

    private fun setupTokenRefreshListener() {
        authTokenRefreshListener.onTokenRefreshFailedCallback = {
            val navController = findNavController(R.id.fc_main)
            navController.popBackStack(R.id.loginFragment, false)
            navController.navigate(R.id.loginFragment)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(ev)
    }
}
