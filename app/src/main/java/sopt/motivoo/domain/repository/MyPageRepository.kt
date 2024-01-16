package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.domain.entity.ResponseMyPageDto
import sopt.motivoo.domain.entity.auth.LoginInfo

interface MyPageRepository {

    suspend fun getUserInfo(): Result<ResponseMyPageDto>
}
