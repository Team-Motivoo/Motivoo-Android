package sopt.motivoo.data.model.request.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestMissionImageDto(
    @SerialName("img_prefix")
    val img_prefix: String
)
