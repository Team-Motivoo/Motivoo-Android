package sopt.motivoo.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import sopt.motivoo.BuildConfig
import sopt.motivoo.data.datasource.local.MotivooStorageImpl.Companion.ACCESS_TOKEN
import sopt.motivoo.data.datasource.local.MotivooStorageImpl.Companion.REFRESH_TOKEN
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
            REFRESH_CODE -> {
                response.close()
                val refreshRequest = chain.request()
                val userId = motivooStorage.userId
                val jsonMediaType = "application/json".toMediaType()
                val jsonBody = """{"user_id": "$userId"}""".toRequestBody(jsonMediaType)

                val refreshRequestBuilder = refreshRequest.newBuilder()
                    .url("${BuildConfig.BASE_URL}oauth/reissue")
                    .addHeader(REFRESH_TOKEN, motivooStorage.refreshToken)
                    .method(refreshRequest.method, jsonBody)
                    .build()

                return chain.proceed(refreshRequestBuilder)
            }
        }
        return response
    }

    companion object {
        private const val REFRESH_CODE = 401
    }
}
