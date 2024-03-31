package sopt.mottivoo.data.datasource.remote

import sopt.mottivoo.data.model.request.auth.RequestLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLoginDto
import sopt.mottivoo.data.model.response.auth.ResponseLogoutDto
import sopt.mottivoo.data.model.response.auth.ResponseWithDrawDto
import sopt.mottivoo.data.service.AuthService
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
