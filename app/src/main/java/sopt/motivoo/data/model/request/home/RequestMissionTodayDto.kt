package sopt.motivoo.data.model.request.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestMissionTodayDto(
    @SerialName("mission_id")
    val missionId: Long,
)
