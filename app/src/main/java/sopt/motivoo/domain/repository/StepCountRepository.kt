package sopt.motivoo.domain.repository

import kotlinx.coroutines.flow.Flow

interface StepCountRepository {
    val myStepCount: Flow<Int>
    suspend fun getMyStepCount(): Int
    suspend fun addMyStepCount(stepCount: (Int) -> Unit)
    suspend fun setMyStepCount(stepCount: Int)
    suspend fun clearMyStepCount()
}
