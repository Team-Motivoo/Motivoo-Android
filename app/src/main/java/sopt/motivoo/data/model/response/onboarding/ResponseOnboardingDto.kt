package sopt.motivoo.data.model.response.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseOnboardingDto(
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
        @SerialName("exercise_level")
        val exerciseLevel: String,
        @SerialName("invite_code")
        val inviteCode: String?,
        @SerialName("user_id")
        val userId: Int
    )
}
