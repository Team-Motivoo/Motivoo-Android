package sopt.mottivoo.data.repository

import sopt.mottivoo.domain.repository.NetworkRepository
import sopt.mottivoo.util.NetworkState
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    networkState: NetworkState
) : NetworkRepository {

    override val networkStateFlow = networkState.networkState
}
