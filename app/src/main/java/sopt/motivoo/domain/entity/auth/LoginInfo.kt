package sopt.motivoo.domain.entity.auth

data class LoginInfo(
    val accessToken: String,
    val nickName: String,
    val id: Long,
    val refreshToken: String,
    val isOnboardingFinished: Boolean,
    val isMatched: Boolean
)
