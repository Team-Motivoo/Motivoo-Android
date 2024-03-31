package sopt.motivoo.data.model.request.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestMissionImageFileDto(
    @SerialName("file_name")
    val fileName: String
)
