package sopt.mottivoo.data.repository

import sopt.mottivoo.data.datasource.remote.AuthDataSource
import sopt.mottivoo.data.model.request.auth.RequestLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLogoutDto
import sopt.mottivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.mottivoo.domain.entity.auth.LoginInfo
import sopt.mottivoo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun postLogin(requestLoginDto: RequestLoginDto): Result<LoginInfo> =
        runCatching { authDataSource.postLogin(requestLoginDto).toLoginInfo() }

    override suspend fun postLogout(): Result<ResponseLogoutDto> =
        runCatching { authDataSource.postLogout() }

    override suspend fun deleteWithDraw(): Result<ResponseWithDrawDto> =
        runCatching { authDataSource.deleteWithDraw() }
}
