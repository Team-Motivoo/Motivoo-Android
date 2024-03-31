package sopt.motivoo.data.model.response.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.onboarding.MatchedInfo

@Serializable
data class ResponsePostInviteCodeDto(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean
) {
    @Serializable
    data class Data(
        @SerialName("is_matched")
        val isMatched: Boolean,
        @SerialName("opponent_user_id")
        val opponentUserId: Long,
        @SerialName("user_id")
        val userId: Long,
    )

    fun toMatchedInfo(): MatchedInfo {
        return MatchedInfo(
            isMatched = data.isMatched,
        )
    }
}
