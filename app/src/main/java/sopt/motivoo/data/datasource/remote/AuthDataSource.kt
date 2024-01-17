package sopt.motivoo.data.datasource.remote

import sopt.motivoo.data.model.request.auth.RequestLoginDto
import sopt.motivoo.data.model.response.auth.ResponseLoginDto
import sopt.motivoo.data.model.response.auth.ResponseLogoutDto
import sopt.motivoo.data.service.AuthService
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
}
