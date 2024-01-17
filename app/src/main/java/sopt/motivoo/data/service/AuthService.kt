package sopt.motivoo.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import sopt.motivoo.data.model.request.auth.RequestLoginDto
import sopt.motivoo.data.model.response.auth.ResponseLoginDto
import sopt.motivoo.data.model.response.auth.ResponseLogoutDto

interface AuthService {
    @POST("oauth/login")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto,
    ): ResponseLoginDto

    @POST("oauth/logout")
    suspend fun postLogout(): ResponseLogoutDto
}
