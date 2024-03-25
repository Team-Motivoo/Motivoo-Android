package sopt.motivoo.data.model.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.auth.LoginInfo

@Serializable
data class ResponseLoginDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: Data,
) {
    @Serializable
    data class Data(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("id")
        val id: Long,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("token_type")
        val tokenType: String,
        @SerialName("is_finished_onboarding")
        val isFinishedOnboarding: Boolean,
        @SerialName("is_matched")
        val isMatched: Boolean
    )

    fun toLoginInfo(): LoginInfo {
        return LoginInfo(
            accessToken = data.accessToken,
            nickName = data.nickname,
            id = data.id,
            refreshToken = data.refreshToken,
            isOnboardingFinished = data.isFinishedOnboarding,
            isMatched = data.isMatched
        )
    }
}
