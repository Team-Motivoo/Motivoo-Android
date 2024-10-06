package sopt.teammotivoo.domain.repository

import retrofit2.http.Body
import sopt.teammotivoo.data.model.request.auth.RequestLoginDto
import sopt.teammotivoo.data.model.response.auth.ResponseLogoutDto
import sopt.teammotivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.teammotivoo.domain.entity.auth.LoginInfo

interface AuthRepository {

    suspend fun postLogin(@Body requestLoginDto: RequestLoginDto): Result<LoginInfo>

    suspend fun postLogout(): Result<ResponseLogoutDto>

    suspend fun deleteWithDraw(): Result<ResponseWithDrawDto>
}
