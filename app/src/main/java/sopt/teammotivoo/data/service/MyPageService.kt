package sopt.teammotivoo.data.service

import retrofit2.http.GET
import sopt.teammotivoo.data.model.response.ResponseMyPageDto

interface MyPageService {
    @GET("/user/me")
    suspend fun getUserInfo(): ResponseMyPageDto
}
