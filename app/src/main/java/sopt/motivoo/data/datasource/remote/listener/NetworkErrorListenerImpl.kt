package sopt.motivoo.data.datasource.remote.listener

import javax.inject.Inject

class NetworkErrorListenerImpl @Inject constructor() : NetworkErrorListener {
    private var onApiCallFailedCallback: (() -> Unit)? = null

    override fun onApiCallFailed() {
        onApiCallFailedCallback?.invoke()
    }

    override fun setOnApiCallFailedCallback(callback: () -> Unit) {
        onApiCallFailedCallback = callback
    }

    override fun clearOnApiCallFailedCallback() {
        onApiCallFailedCallback = null
    }
}
