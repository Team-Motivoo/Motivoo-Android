package sopt.motivoo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sopt.motivoo.domain.repository.NetworkRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkRepository: NetworkRepository
) : ViewModel() {

    val networkState: LiveData<Boolean> = networkRepository.networkStateLiveData
    val isLoading: LiveData<Boolean> = networkRepository.isLoading
}
