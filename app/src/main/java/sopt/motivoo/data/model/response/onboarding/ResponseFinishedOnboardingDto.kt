package sopt.motivoo.data.model.response.onboarding


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding

@Serializable
data class ResponseFinishedOnboardingDto(
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
        @SerialName("is_finished_onboarding")
        val isFinishedOnboarding: Boolean
    )

    fun toFinishedOnboarding(): FinishedOnboarding {
        return FinishedOnboarding(
            isFinishedOnboarding = data.isFinishedOnboarding
        )
    }
}
