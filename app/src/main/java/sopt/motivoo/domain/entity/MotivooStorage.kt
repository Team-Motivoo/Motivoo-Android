package sopt.motivoo.domain.entity

interface MotivooStorage {
    var stepCount: Int
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var isUserMatched: Boolean
    var userId: Long
    var isFinishedOnboarding: Boolean
    var inviteCode: String
    fun logout()
    fun clear()
}
