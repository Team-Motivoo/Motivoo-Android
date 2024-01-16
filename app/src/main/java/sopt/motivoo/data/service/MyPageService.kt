package sopt.motivoo.data.service

import retrofit2.http.GET
import sopt.motivoo.domain.entity.ResponseMyPageDto

interface MyPageService {
    @GET("/user/me")
    suspend fun getUserInfo(): ResponseMyPageDto
}
