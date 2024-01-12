package sopt.motivoo.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.Response
import sopt.motivoo.data.datasource.local.MotivooStorageImpl.Companion.ACCESS_TOKEN
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val motivooStorage: MotivooStorage,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val isAutoLogin = motivooStorage.isLogin

        val authRequestBuilder = originalRequest.newBuilder()
        if (isAutoLogin) {
            authRequestBuilder.addHeader(ACCESS_TOKEN, motivooStorage.accessToken)
        } else {
            authRequestBuilder.removeHeader(ACCESS_TOKEN)
        }

        val authRequest = authRequestBuilder.build()

        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                // TODO: 토큰 재발급 API 연동
            }
        }
        return response
    }
}
