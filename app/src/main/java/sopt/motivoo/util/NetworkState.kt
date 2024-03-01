package sopt.motivoo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NetworkState(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkState =
        MutableSharedFlow<Boolean>(replay = 1)
    val networkState = _networkState.asSharedFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkState.tryEmit(true)
        }

        override fun onLost(network: Network) {
            _networkState.tryEmit(false)
        }
    }

    init {
        val network = connectivityManager.activeNetwork
        val initialState =
            network != null && connectivityManager.getNetworkCapabilities(network)?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            } ?: false

        _networkState.tryEmit(initialState)

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}
