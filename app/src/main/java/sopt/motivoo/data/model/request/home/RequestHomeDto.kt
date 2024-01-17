package sopt.motivoo.data.model.request.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestHomeDto(
    @SerialName("my_step_count")
    val myStepCount: Int,
    @SerialName("opponent_step_count")
    val opponentStepCount: Int,
)
