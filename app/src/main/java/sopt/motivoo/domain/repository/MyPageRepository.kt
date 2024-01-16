package sopt.motivoo.domain.repository

import sopt.motivoo.domain.entity.ResponseMyPageDto

interface MyPageRepository {

    suspend fun getUserInfo(): Result<ResponseMyPageDto>
}
