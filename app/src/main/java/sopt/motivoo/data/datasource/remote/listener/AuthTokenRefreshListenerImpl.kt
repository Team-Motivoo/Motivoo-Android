package sopt.motivoo.data.datasource.remote.listener

import javax.inject.Inject

class AuthTokenRefreshListenerImpl @Inject constructor() : AuthTokenRefreshListener {
    private var onTokenRefreshFailedCallback: (() -> Unit)? = null

    override fun onTokenRefreshFailed() {
        onTokenRefreshFailedCallback?.invoke()
    }

    override fun setOnTokenRefreshFailedCallback(callback: () -> Unit) {
        onTokenRefreshFailedCallback = callback
    }

    override fun clearOnTokenRefreshFailedCallback() {
        onTokenRefreshFailedCallback = null
    }
}
