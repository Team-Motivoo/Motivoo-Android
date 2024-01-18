package sopt.motivoo.domain.entity

interface MotivooStorage {
    var nickName: String
    var myStepCount: Int
    var otherStepCount: Int
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var isUserMatched: Boolean
    var userId: Long
    var isFinishedOnboarding: Boolean
    var isFinishedPermission: Boolean
    var inviteCode: String
    var otherId: Long
    var myGoalStepCount: Int
    var otherGoalStepCount: Int
    fun logout()
    fun clear()
}
