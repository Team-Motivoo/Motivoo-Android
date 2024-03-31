package sopt.mottivoo.domain.repository

import retrofit2.http.Body
import sopt.mottivoo.data.model.request.auth.RequestLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLogoutDto
import sopt.mottivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.mottivoo.domain.entity.auth.LoginInfo

interface AuthRepository {

    suspend fun postLogin(@Body requestLoginDto: RequestLoginDto): Result<LoginInfo>

    suspend fun postLogout(): Result<ResponseLogoutDto>

    suspend fun deleteWithDraw(): Result<ResponseWithDrawDto>
}
