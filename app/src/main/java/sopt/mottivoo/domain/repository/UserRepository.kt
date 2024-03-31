package sopt.mottivoo.domain.repository

interface UserRepository {
    suspend fun getUserId(): Int
    suspend fun setUserId(userId: Int)
    suspend fun clearUserId()
}
