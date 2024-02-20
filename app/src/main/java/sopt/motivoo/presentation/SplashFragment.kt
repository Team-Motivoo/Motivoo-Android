package sopt.motivoo.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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
        if (checkNetworkState()) {
            showSplash()
        } else {
            showNetworkErrorDialog()
        }
    }

    // TODO-l2zh 다이얼로그 나오면 수정
    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("네트워크 오류")
            setMessage("네트워크 연결을 확인해주세요.")
            setPositiveButton("확인") { _, _ ->
                activity?.finish()
            }
            setCancelable(false)
            create().show()
        }
    }

    private fun showSplash() {
        lifecycleScope.launch {
            delay(SPLASH_DISPLAY_LENGTH)
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

    private fun checkNetworkState(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 2000L
    }
}
