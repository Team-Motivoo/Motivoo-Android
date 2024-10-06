package sopt.teammotivoo.data.repository

import sopt.teammotivoo.data.datasource.remote.AuthDataSource
import sopt.teammotivoo.data.model.request.auth.RequestLoginDto
import sopt.teammotivoo.data.model.response.auth.ResponseLogoutDto
import sopt.teammotivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.teammotivoo.domain.entity.auth.LoginInfo
import sopt.teammotivoo.domain.repository.AuthRepository
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
