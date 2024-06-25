package sopt.teammotivoo.domain.repository

import sopt.teammotivoo.data.model.response.ResponseMyPageDto

interface MyPageRepository {

    suspend fun getUserInfo(): Result<ResponseMyPageDto>
}
