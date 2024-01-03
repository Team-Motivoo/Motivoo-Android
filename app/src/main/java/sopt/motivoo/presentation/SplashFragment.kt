package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentSplashBinding
import sopt.motivoo.util.binding.BindingFragment

class SplashFragment : BindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSplash.setOnClickListener {
            navigateToLogin()
        }

        binding.btnWebview.setOnClickListener {
            navigateToWebView()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun navigateToWebView() {
        val action = SplashFragmentDirections
            .actionSplashFragmentToWebViewFragment("https://velog.io/@gnwjd309/GitHub-commit-history")
        findNavController().navigate(action)
    }
}
