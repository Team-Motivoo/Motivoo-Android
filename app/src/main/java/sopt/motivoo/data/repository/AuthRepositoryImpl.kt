package sopt.motivoo.data.repository


import sopt.motivoo.data.datasource.remote.AuthDataSource
import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.domain.entity.auth.LoginInfo
import sopt.motivoo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun postLogin(requestLoginDto: RequestLoginDto): Result<LoginInfo> =
        runCatching { authDataSource.postLogin(requestLoginDto).toLoginInfo() }
}
