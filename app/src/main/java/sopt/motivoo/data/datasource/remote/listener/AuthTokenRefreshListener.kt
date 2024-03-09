package sopt.motivoo.data.datasource.remote.listener

interface AuthTokenRefreshListener {
    fun onTokenRefreshFailed()
    fun setOnTokenRefreshFailedCallback(callback: () -> Unit)
    fun clearOnTokenRefreshFailedCallback()
}
