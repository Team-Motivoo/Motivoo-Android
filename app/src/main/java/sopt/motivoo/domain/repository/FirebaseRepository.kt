package sopt.motivoo.domain.repository

import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun getStepCount(id: Long): Long?
    fun getUpdatedStepCount(otherId: Long): Flow<Int>
    fun setUserStepCount(userId: Long, myStepCount: Int)
}
