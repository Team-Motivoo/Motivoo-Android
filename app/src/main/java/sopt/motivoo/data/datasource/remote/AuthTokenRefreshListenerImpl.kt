package sopt.motivoo.data.datasource.remote

import javax.inject.Inject

class AuthTokenRefreshListenerImpl @Inject constructor() : AuthTokenRefreshListener {
    lateinit var onTokenRefreshFailedCallback: (() -> Unit)

    override fun onTokenRefreshFailed() {
        if (this::onTokenRefreshFailedCallback.isInitialized) {
            onTokenRefreshFailedCallback()
        }
    }
}
