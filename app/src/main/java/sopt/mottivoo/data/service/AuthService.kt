package sopt.mottivoo.data.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import sopt.mottivoo.data.model.request.auth.RequestLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLogoutDto
import sopt.mottivoo.data.model.response.auth.ResponseWithDrawDto

interface AuthService {
    @POST("oauth/login")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto,
    ): ResponseLoginDto

    @POST("oauth/logout")
    suspend fun postLogout(): ResponseLogoutDto

    @DELETE("withdraw")
    suspend fun deleteWithDraw(): ResponseWithDrawDto
}
