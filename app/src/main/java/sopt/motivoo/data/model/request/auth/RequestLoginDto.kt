package sopt.motivoo.data.model.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    @SerialName("social_access_token")
    val socialAccessToken: String,
    @SerialName("token_type")
    val tokenType: String
)
