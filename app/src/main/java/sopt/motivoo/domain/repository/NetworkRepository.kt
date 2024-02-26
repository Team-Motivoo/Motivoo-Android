package sopt.motivoo.domain.repository

import androidx.lifecycle.LiveData

interface NetworkRepository {

    val networkStateLiveData: LiveData<Boolean>
    val isLoading: LiveData<Boolean>
    fun setLoading(isLoading: Boolean)
}
