package sopt.motivoo.domain.repository


import retrofit2.Response
import retrofit2.http.Body
import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.data.model.response.ResponseLoginDto
import sopt.motivoo.domain.entity.auth.LoginInfo

interface AuthRepository {

    suspend fun postLogin(@Body requestLoginDto: RequestLoginDto): Result<LoginInfo>
}
