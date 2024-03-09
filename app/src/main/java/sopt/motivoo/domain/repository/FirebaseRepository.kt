package sopt.motivoo.domain.repository

import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getStepCount(id: Long): Flow<Int>
    fun getUpdatedStepCount(otherId: Long): Flow<Int>
    fun setUserStepCount(userId: Long, myStepCount: Int)
}
