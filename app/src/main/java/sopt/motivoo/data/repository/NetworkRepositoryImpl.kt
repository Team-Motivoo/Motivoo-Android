package sopt.motivoo.data.repository

import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.util.NetworkState
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    networkState: NetworkState
) : NetworkRepository {

    override val networkStateFlow = networkState.networkState
}
