package sopt.motivoo.data.model.request.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestOnboardingDto(
    @SerialName("age")
    val age: Int,
    @SerialName("exercise_count")
    val exerciseCount: String,
    @SerialName("exercise_note")
    val exerciseNote: List<String>?,
    @SerialName("exercise_time")
    val exerciseTime: String,
    @SerialName("exercise_type")
    val exerciseType: String,
    @SerialName("is_exercise")
    val isExercise: Boolean,
    @SerialName("type")
    val type: String
)
