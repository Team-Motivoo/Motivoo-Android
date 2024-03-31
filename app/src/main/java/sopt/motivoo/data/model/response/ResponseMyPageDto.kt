package sopt.motivoo.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.mypage.UserInfo

@Serializable
data class ResponseMyPageDto(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: UserInfoData,
) {
    @Serializable
    data class UserInfoData(
        @SerialName("user_nickname") val userNickname: String,
        @SerialName("user_age") val userAge: Int,
        @SerialName("user_type") val userType: String,
    )

    fun toUserInfo(): UserInfo {
        return UserInfo(
            userNickname = data.userNickname,
            userAge = data.userAge,
            userType = data.userType,
        )
    }
}
