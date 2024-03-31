package sopt.motivoo.domain.repository

import sopt.motivoo.data.model.response.ResponseMyPageDto

interface MyPageRepository {

    suspend fun getUserInfo(): Result<ResponseMyPageDto>
}
