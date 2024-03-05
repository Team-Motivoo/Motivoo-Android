package sopt.motivoo.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListener
import sopt.motivoo.data.datasource.remote.listener.NetworkErrorListener
import sopt.motivoo.databinding.ActivityMainBinding
import sopt.motivoo.util.extension.checkNetworkState
import sopt.motivoo.util.extension.colorOf
import sopt.motivoo.util.extension.hideKeyboard
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.findStartDestination
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var authTokenRefreshListener: AuthTokenRefreshListener

    @Inject
    lateinit var networkErrorListener: NetworkErrorListener

    @Inject
    lateinit var mainDispatcher: CoroutineDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MOTIVOOAOS)
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkRedirectToLogin()
        initView()
        setupTokenRefreshListener()
        setupApiCallFailed()
        collectData()
    }

    private fun collectData() {
        mainViewModel.isLoading.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach { isLoading ->
            val navController: NavController = findNavController(R.id.fc_main)
            if (isLoading && navController.currentDestination?.id != R.id.loadingFragment) {
                navController.navigate(R.id.loadingFragment)
            } else if (!isLoading && navController.currentDestination?.id == R.id.loadingFragment) {
                navController.popBackStack()
            }
        }.launchIn(lifecycleScope)

        mainViewModel.networkState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach { isConnected ->
            if (!isConnected) {
                showNetworkErrorDialog()
            }
        }.launchIn(lifecycleScope)
    }

    private fun checkRedirectToLogin() {
        val redirectToLogin = intent.getBooleanExtra(REDIRECT_TO_LOGIN, false)

        if (redirectToLogin) {
            val bundle = Bundle().apply {
                putBoolean(REDIRECT_TO_LOGIN, true)
            }

            val navController = findNavController(R.id.fc_main)
            navController.setGraph(R.navigation.navigation_main, bundle)
        }
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
            bnvMain.setOnItemReselectedListener(null)
        }
        navController?.let { setBottomVisible(it) }
        navController?.let { setStatusBarColor(it) }

        binding.includeToolbar.tvToolbarBack.setOnSingleClickListener { navController?.popBackStack() }
    }

    private fun setStatusBarColor(navController: NavController) {
        val grayStatusBarDestinations = setOf(
            R.id.myPageFragment,
            R.id.myInfoFragment,
            R.id.myExerciseInfoFragment,
            R.id.loadingFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            window.statusBarColor = if (destination.id in grayStatusBarDestinations) {
                colorOf(R.color.gray_100_F4F5F9)
            } else {
                colorOf(R.color.white_FFFFFF)
            }
        }
    }

    private fun setBottomVisible(navController: NavController) {
        val bottomVisibleDestinations = setOf(
            R.id.homeFragment,
            R.id.myPageFragment,
            R.id.exerciseFragment,
            R.id.myInfoFragment,
            R.id.myExerciseInfoFragment,
            R.id.myServiceOutFragment,
            R.id.myLogoutFragment,
            R.id.homeConfirmDialogFragment,
            R.id.withdrawalFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvMain.visibility = if (destination.id in bottomVisibleDestinations) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setTopVisible(navController: NavController) {
        val topVisibleDestinations = setOf(
            R.id.ageQuestionFragment,
            R.id.doExerciseQuestionFragment,
            R.id.frequencyQuestionFragment,
            R.id.soreSpotQuestionFragment,
            R.id.timeQuestionFragment,
            R.id.whatExerciseQuestionFragment,
            R.id.whatActivityQuestionFragment
        )
        val backInvisibleDestinations = setOf(R.id.ageQuestionFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.clOnboardingToolbar.visibility =
                if (destination.id in topVisibleDestinations) View.VISIBLE else View.GONE
            binding.includeToolbar.tvToolbarBack.visibility =
                if (destination.id in backInvisibleDestinations) View.INVISIBLE else View.VISIBLE
            binding.onboardingProgress.progress = getProgressValue(destination.id)
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
        authTokenRefreshListener.setOnTokenRefreshFailedCallback {
            CoroutineScope(mainDispatcher).launch {
                val navController: NavController = findNavController(R.id.fc_main)
                val startDestinationId = navController.findStartDestination().id
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(startDestinationId, true)
                    .build()

                navController.navigate(R.id.loginFragment, null, navOptions)
            }
        }
    }

    private fun setupApiCallFailed() {
        networkErrorListener.setOnApiCallFailedCallback {
            CoroutineScope(mainDispatcher).launch {
                val navController: NavController = findNavController(R.id.fc_main)
                val startDestinationId = navController.findStartDestination().id
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(startDestinationId, true)
                    .build()

                navController.navigate(R.id.networkErrorFragment, null, navOptions)
            }
        }
    }

    private fun showNetworkErrorDialog() {
        // TODO-l2zh 다이얼로그 수정
        AlertDialog.Builder(this).apply {
            setTitle("네트워크 오류")
            setMessage("네트워크 연결을 확인해주세요.")
            setPositiveButton("재시도") { dialog, _ ->
                if (checkNetworkState()) {
                    dialog.dismiss()
                } else {
                    showNetworkErrorDialog()
                }
            }
            setCancelable(false)
            create().show()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        authTokenRefreshListener.clearOnTokenRefreshFailedCallback()
        super.onDestroy()
    }

    companion object {
        const val REDIRECT_TO_LOGIN = "redirectToLogin"
    }
}
