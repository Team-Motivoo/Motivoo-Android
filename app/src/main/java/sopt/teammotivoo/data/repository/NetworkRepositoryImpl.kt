package sopt.teammotivoo.data.repository

import sopt.teammotivoo.domain.repository.NetworkRepository
import sopt.teammotivoo.util.NetworkState
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    networkState: NetworkState
) : NetworkRepository {

    override val networkStateFlow = networkState.networkState
}
