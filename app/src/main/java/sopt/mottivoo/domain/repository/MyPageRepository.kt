package sopt.mottivoo.domain.repository

import sopt.mottivoo.data.model.response.ResponseMyPageDto

interface MyPageRepository {

    suspend fun getUserInfo(): Result<ResponseMyPageDto>
}
