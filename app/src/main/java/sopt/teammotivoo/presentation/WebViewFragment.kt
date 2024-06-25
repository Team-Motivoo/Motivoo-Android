package sopt.teammotivoo.presentation

import android.os.Bundle
import android.view.View
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.FragmentWebviewBinding
import sopt.teammotivoo.util.binding.BindingFragment

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
