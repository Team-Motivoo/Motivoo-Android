package sopt.motivoo.data.datasource.remote

import sopt.motivoo.data.model.request.RequestLoginDto
import sopt.motivoo.data.model.response.ResponseLoginDto
import sopt.motivoo.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postLogin(
        requestLogin: RequestLoginDto
    ): ResponseLoginDto =
        authService.postLogin(requestLogin)
}
