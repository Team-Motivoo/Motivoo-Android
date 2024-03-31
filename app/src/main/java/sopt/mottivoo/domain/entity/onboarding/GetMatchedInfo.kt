package sopt.mottivoo.domain.entity.onboarding

data class GetMatchedInfo(
    val userId: Int,
    val opponentUserId: Int,
    val isMatched: Boolean,
)
