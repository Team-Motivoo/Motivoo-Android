package sopt.motivoo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import sopt.motivoo.data.datasource.local.StepCountLocalDataSource
import sopt.motivoo.domain.repository.StepCountRepository
import javax.inject.Inject

class StepCountRepositoryImpl @Inject constructor(
    private val stepCountLocalDataSource: StepCountLocalDataSource,
) : StepCountRepository {
    override val myStepCount: Flow<Int>
        get() = stepCountLocalDataSource.myStepCount

    override suspend fun getMyStepCount(): Int {
        return stepCountLocalDataSource.myStepCount.first()
    }

    override suspend fun addMyStepCount(stepCount: (Int) -> Unit) {
        stepCountLocalDataSource.addMyStepCount(stepCount)
    }

    override suspend fun setMyStepCount(stepCount: Int) {
        stepCountLocalDataSource.setMyStepCount(stepCount)
    }

    override suspend fun clearMyStepCount() {
        stepCountLocalDataSource.removeMyStepCount()
    }
}
