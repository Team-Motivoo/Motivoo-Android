package sopt.teammotivoo.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import sopt.teammotivoo.domain.repository.NetworkRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkRepository: NetworkRepository
) : ViewModel() {

    val networkState: SharedFlow<Boolean> = networkRepository.networkStateFlow
}
