package sopt.motivoo.domain.entity

import android.telecom.Call
import retrofit2.http.GET

interface MotivooStorage {
    var nickName: String
    var stepCount: Int
    var accessToken: String
    var refreshToken: String
    var isUserLoggedIn: Boolean
    var userId: Long
    fun clear()
}
