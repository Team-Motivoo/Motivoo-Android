package sopt.motivoo.data.model.response.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithDrawDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean
)
