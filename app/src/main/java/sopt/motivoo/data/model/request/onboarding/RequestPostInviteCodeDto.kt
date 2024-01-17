package sopt.motivoo.data.model.request.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostInviteCodeDto(
    @SerialName("invite_code")
    val inviteCode: String
)
