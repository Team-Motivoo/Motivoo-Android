package sopt.mottivoo.data.service

import retrofit2.http.GET
import sopt.mottivoo.data.model.response.ResponseMyPageDto

interface MyPageService {
    @GET("/user/me")
    suspend fun getUserInfo(): ResponseMyPageDto
}
