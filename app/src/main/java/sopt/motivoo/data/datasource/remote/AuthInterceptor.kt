package sopt.motivoo.data.datasource.remote

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import sopt.motivoo.BuildConfig
import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListener
import sopt.motivoo.data.model.response.auth.ResponseReissueDto
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val motivooStorage: MotivooStorage,
    private val json: Json,
    private val authTokenRefreshListener: AuthTokenRefreshListener,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val isAutoLoginPossible = motivooStorage.isUserLoggedIn

        val originalRequest = chain.request()
        val authRequest = originalRequest.newAuthBuilder()
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
                    .addHeader(AUTHORIZATION, motivooStorage.refreshToken)
                    .post(jsonBody)
                    .build()

                val refreshTokenResponse = chain.proceed(refreshRequestBuilder)

                if (refreshTokenResponse.isSuccessful) {

                    val responseRefresh =
                        json.decodeFromString<ResponseReissueDto>(
                            refreshTokenResponse.body?.string()
                                ?: throw IllegalStateException("\"refreshTokenResponse is null $refreshTokenResponse\"")
                        )

                    with(motivooStorage) {
                        accessToken = responseRefresh.data.accessToken
                        refreshToken = responseRefresh.data.refreshToken
                    }

                    refreshTokenResponse.close()

                    val newRequest = originalRequest.newAuthBuilder()
                    return chain.proceed(newRequest)
                } else {
                    authTokenRefreshListener.onTokenRefreshFailed()
                    motivooStorage.clear()
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(AUTHORIZATION, motivooStorage.accessToken).build()

    companion object {
        private const val REFRESH_CODE = 401
        private const val AUTHORIZATION = "Authorization"
    }
}
