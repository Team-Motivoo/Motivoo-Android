package sopt.motivoo.domain.entity.home

data class HomeData(
    val userType: String,
    val userId: Long,
    val userGoalStepCount: Int,
    val opponentUserId: Long,
    val opponentUserGoalStepCount: Int,
    val isStepCountCompleted: Boolean,
    val isOpponentUserWithdraw: Boolean,
)
