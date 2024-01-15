package sopt.motivoo.domain.entity

interface MotivooStorage {
    var nickName: String
    var stepCount: Int
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var userId: Long
    var isFinishedOnboarding: Boolean
    fun clear()
}
