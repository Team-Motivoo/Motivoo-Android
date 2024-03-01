package sopt.motivoo.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface NetworkRepository {

    val networkStateFlow: SharedFlow<Boolean>
    val isLoading: SharedFlow<Boolean>
    suspend fun setLoading(isLoading: Boolean)
}
