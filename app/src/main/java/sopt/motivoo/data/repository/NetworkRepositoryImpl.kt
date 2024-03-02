package sopt.motivoo.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.util.NetworkState
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    networkState: NetworkState
) : NetworkRepository {

    override val networkStateFlow = networkState.networkState

    private val _isLoading = MutableSharedFlow<Boolean>(replay = 1)
    override val isLoading get() = _isLoading.asSharedFlow()

    override suspend fun setLoading(isLoading: Boolean) {
        _isLoading.emit(isLoading)
    }
}
