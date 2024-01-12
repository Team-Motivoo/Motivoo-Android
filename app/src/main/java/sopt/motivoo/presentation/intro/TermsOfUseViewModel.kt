package sopt.motivoo.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TermsOfUseViewModel : ViewModel() {

    val termsOfUseState = MutableStateFlow(false)
    val termsGetInfoState = MutableStateFlow(false)
    val termsUseInfoState = MutableStateFlow(false)
    val termsAllCheckState = MutableStateFlow(false)

    init {
        observeAllCheckState()
    }

    val isAllTermValid: StateFlow<Boolean> =
        combine(
            termsOfUseState,
            termsGetInfoState,
            termsUseInfoState,
        ) { termsOfUseState, termsGetInfoState, termsUseInfoState ->
            termsOfUseState && termsGetInfoState && termsUseInfoState
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private fun observeAllCheckState() {
        viewModelScope.launch {
            termsAllCheckState.collect { allChecked ->
                termsOfUseState.value = allChecked
                termsGetInfoState.value = allChecked
                termsUseInfoState.value = allChecked
            }
        }
    }
}
