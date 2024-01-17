package sopt.motivoo.domain.entity

interface MotivooStorage {
    var nickName: String
    var myStepCount: Int
    var otherStepCount: Int
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var userId: Long
    var myGoalStepCount: Int
    var otherGoalStepCount: Int
    fun clear()
}
