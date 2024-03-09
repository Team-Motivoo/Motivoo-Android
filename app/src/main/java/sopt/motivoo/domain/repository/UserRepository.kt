package sopt.motivoo.domain.repository

interface UserRepository {
    suspend fun getUserId(): Int
    suspend fun setUserId(userId: Int)
}