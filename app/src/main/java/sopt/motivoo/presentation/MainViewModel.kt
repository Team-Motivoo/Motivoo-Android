package sopt.motivoo.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sopt.motivoo.util.NetworkStateLiveData
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkState: NetworkStateLiveData
) : ViewModel() {

    val networkStateLiveData = networkState
}
