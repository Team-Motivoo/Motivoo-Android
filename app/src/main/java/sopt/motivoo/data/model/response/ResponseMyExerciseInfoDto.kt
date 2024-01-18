package sopt.motivoo.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyExerciseInfoDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: MyExerciseInfo,
) {
    @Serializable
    data class MyExerciseInfo(
        @SerialName("is_exercise")
        val isExercise: Boolean,
        @SerialName("exercise_type")
        val exerciseType: String,
        @SerialName("exercise_frequency")
        val exerciseFrequency: String,
        @SerialName("exercise_time")
        val exerciseTime: String,
        @SerialName("health_notes")
        val healthNotes: List<String>,
    )
}
