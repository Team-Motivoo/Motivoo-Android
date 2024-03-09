package sopt.motivoo.data.repository

import kotlinx.coroutines.flow.first
import sopt.motivoo.data.datasource.local.UserLocalDataSource
import sopt.motivoo.domain.repository.UserRepository
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
}
