package sopt.motivoo.data.model.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.home.HomeData

@Serializable
data class ResponseHomeDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ResponseHomeDataDto,
) {
    @Serializable
    data class ResponseHomeDataDto(
        @SerialName("user_type")
        val userType: String,
        @SerialName("user_id")
        val userId: Long,
        @SerialName("user_goal_step_count")
        val userGoalStepCount: Int,
        @SerialName("opponent_user_id")
        val opponentUserId: Long,
        @SerialName("opponent_user_goal_step_count")
        val opponentUserGoalStepCount: Int,
        @SerialName("is_step_count_completed")
        val isStepCountCompleted: Boolean,
        @SerialName("is_opponent_user_withdraw")
        val isOpponentUserWithdraw: Boolean,
    )

    fun toHomeData(): HomeData = HomeData(
        userType = data.userType,
        userId = data.userId,
        userGoalStepCount = data.userGoalStepCount,
        opponentUserGoalStepCount = data.opponentUserGoalStepCount,
        isStepCountCompleted = data.isStepCountCompleted,
        opponentUserId = data.opponentUserId,
        isOpponentUserWithdraw = data.isOpponentUserWithdraw
    )
}
