package sopt.motivoo.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.data.model.response.ResponseLoginDto

interface AuthService {
    @POST("oauth/login")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto,
    ): ResponseLoginDto
}
