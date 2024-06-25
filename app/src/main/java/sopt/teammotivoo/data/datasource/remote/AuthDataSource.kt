package sopt.teammotivoo.data.datasource.remote

import sopt.teammotivoo.data.model.request.auth.RequestLoginDto
import sopt.teammotivoo.data.model.response.auth.ResponseLoginDto
import sopt.teammotivoo.data.model.response.auth.ResponseLogoutDto
import sopt.teammotivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.teammotivoo.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postLogin(
        requestLogin: RequestLoginDto
    ): ResponseLoginDto =
        authService.postLogin(requestLogin)

    suspend fun postLogout(): ResponseLogoutDto =
        authService.postLogout()

    suspend fun deleteWithDraw(): ResponseWithDrawDto =
        authService.deleteWithDraw()
}
