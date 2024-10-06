package sopt.teammotivoo.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface NetworkRepository {

    val networkStateFlow: SharedFlow<Boolean>
}
