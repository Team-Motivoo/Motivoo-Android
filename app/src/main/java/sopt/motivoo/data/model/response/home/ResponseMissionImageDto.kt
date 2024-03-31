package sopt.motivoo.data.model.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.home.MissionImageData

@Serializable
data class ResponseMissionImageDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ResponseMissionImageDataDto,
) {
    @Serializable
    data class ResponseMissionImageDataDto(
        @SerialName("url")
        val imgPresignedUrl: String,
        @SerialName("file_name")
        val fileName: String,
    )
    fun toMissionImageData(): MissionImageData = MissionImageData(
        imgPresignedUrl = data.imgPresignedUrl,
        fileName = data.fileName
    )
}
