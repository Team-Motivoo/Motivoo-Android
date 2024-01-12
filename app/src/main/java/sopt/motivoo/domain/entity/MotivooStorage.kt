package sopt.motivoo.domain.entity

interface MotivooStorage {
    var nickName: String
    var stepCount: Int
    var accessToken: String
    var refreshToken: String
    var isLogin: Boolean
    var userId: Long
    fun clear()
}
