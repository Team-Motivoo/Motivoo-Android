package sopt.motivoo.data.datasource.remote.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import sopt.motivoo.data.datasource.remote.listener.NetworkErrorListener
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val networkErrorListener: NetworkErrorListener,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(chain.request())

        when (response.code) {

            in SERVER_ERROR_START..SERVER_ERROR_END -> {
                networkErrorListener.onApiCallFailed()

                response.close()
                response = chain.proceed(request)
            }

        }
        return response
    }

    companion object {
        private const val SERVER_ERROR_START = 500
        private const val SERVER_ERROR_END = 599
    }
}
