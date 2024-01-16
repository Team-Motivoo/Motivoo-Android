package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentWebviewBinding
import sopt.motivoo.util.binding.BindingFragment

class WebViewFragment : BindingFragment<FragmentWebviewBinding>(R.layout.fragment_webview) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        startWebView()
    }

    private fun initWebView() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
        }
    }

    private fun startWebView() {
        val url = arguments?.let { WebViewFragmentArgs.fromBundle(it).url }
        url?.let {
            binding.webView.loadUrl(it)
        }
    }
}
