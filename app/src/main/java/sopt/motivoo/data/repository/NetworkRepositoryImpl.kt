package sopt.motivoo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.util.NetworkStateLiveData
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    networkState: NetworkStateLiveData
) : NetworkRepository {

    override val networkStateLiveData = networkState

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> = _isLoading

    override fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}
