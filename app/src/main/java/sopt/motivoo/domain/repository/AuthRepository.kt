package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.auth.RequestLoginDto
import sopt.motivoo.domain.entity.auth.LoginInfo

interface AuthRepository {

    suspend fun postLogin(@Body requestLoginDto: RequestLoginDto): Result<LoginInfo>
}
