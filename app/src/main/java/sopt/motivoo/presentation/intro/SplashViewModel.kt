package sopt.motivoo.presentation.intro

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sopt.motivoo.util.NavigationDecider
import sopt.motivoo.util.NavigationEvent
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigationDecider: NavigationDecider
) : ViewModel() {

    private val _navigationEvent =
        MutableStateFlow<NavigationEvent>(NavigationEvent.Init)
    val navigationEvent get() = _navigationEvent.asStateFlow()

    init {
        checkNavigateState()
    }

    private fun checkNavigateState() {
        _navigationEvent.value = navigationDecider.determineNavigationDestination()
    }
}
