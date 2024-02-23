package sopt.motivoo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData

class NetworkStateLiveData(context: Context) : LiveData<Boolean>() {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        val network = connectivityManager.activeNetwork

        postValue(
            network != null && connectivityManager.getNetworkCapabilities(network)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            } ?: false
        )

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
