package sopt.teammotivoo.data.repository

import kotlinx.coroutines.flow.first
import sopt.teammotivoo.data.datasource.local.UserLocalDataSource
import sopt.teammotivoo.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override suspend fun getUserId(): Int {
        return userLocalDataSource.userId.first()
    }

    override suspend fun setUserId(userId: Int) {
        userLocalDataSource.setUserId(userId)
    }

    override suspend fun clearUserId() {
        userLocalDataSource.clearUserId()
    }
}
