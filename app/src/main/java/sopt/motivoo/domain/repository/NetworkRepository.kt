package sopt.motivoo.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface NetworkRepository {

    val networkStateFlow: SharedFlow<Boolean>
}
