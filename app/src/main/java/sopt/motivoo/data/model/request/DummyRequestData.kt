package sopt.motivoo.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyRequestData(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
)
