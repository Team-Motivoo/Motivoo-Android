package sopt.motivoo.data.model.response.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo

@Serializable
data class ResponseGetInviteCodeDto(
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
        @SerialName("user_id")
        val userId: Int,
        @SerialName("is_matched")
        val isMatched: Boolean,
        @SerialName("invite_code")
        val inviteCode: String
    )

    fun toGetInviteCode(): InviteCodeInfo {
        return InviteCodeInfo(
            inviteCode = data.inviteCode,
        )
    }
}
