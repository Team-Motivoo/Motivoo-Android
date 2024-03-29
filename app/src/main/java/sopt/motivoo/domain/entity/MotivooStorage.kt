package sopt.motivoo.domain.entity

interface MotivooStorage {
    var nickName: String
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var isUserMatched: Boolean
    var userId: Long
    var isFinishedOnboarding: Boolean
    var isFinishedPermission: Boolean
    var isFinishedTermsOfUse: Boolean
    var myGoalStepCount: Int
    var otherGoalStepCount: Int
    fun logout()
    fun clear()
}
