package sopt.motivoo.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.ActivityMainBinding
import sopt.motivoo.util.extension.hideKeyboard

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
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
    }

    private fun setBottomVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvMain.visibility = if (destination.id in listOf(
                    R.id.homeFragment,
                    R.id.myPageFragment,
                    R.id.exerciseFragment,
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
                    R.id.frequencyExerciseQuestionFragment,
                    R.id.soreSpotQuestionFragment,
                    R.id.timeExerciseQuestionFragment,
                    R.id.whatExerciseQuestionFragment,
                )
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

            val progressValue = getProgressValue(destination.id.toFloat())
            binding.onboardingProgress.progress = progressValue
        }
    }

    private fun getProgressValue(destinationId: Float): Float {
        return when (destinationId) {
            R.id.ageQuestionFragment.toFloat() -> 1f
            R.id.doExerciseQuestionFragment.toFloat() -> 2f
            R.id.whatExerciseQuestionFragment.toFloat() -> 3f
            R.id.frequencyExerciseQuestionFragment.toFloat() -> 4f
            R.id.timeExerciseQuestionFragment.toFloat() -> 5f
            R.id.soreSpotQuestionFragment.toFloat() -> 6f
            else -> 0f
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(ev)
    }
}
