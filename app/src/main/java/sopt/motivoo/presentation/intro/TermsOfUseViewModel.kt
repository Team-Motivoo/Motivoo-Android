package sopt.motivoo.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TermsOfUseViewModel : ViewModel() {

    val termsOfUseState = MutableStateFlow(false)
    val termsGetInfoState = MutableStateFlow(false)
    val termsUseInfoState = MutableStateFlow(false)
    val termsAllCheckState = MutableStateFlow(false)

    init {
        observeIndividualCheckStates()
    }

    private fun observeIndividualCheckStates() {
        viewModelScope.launch {
            combine(
                termsOfUseState,
                termsGetInfoState,
                termsUseInfoState
            ) { termsOfUse, termsGetInfo, termsUseInfo ->
                termsOfUse && termsGetInfo && termsUseInfo
            }.collect { allTermsAgreed ->
                termsAllCheckState.value = allTermsAgreed
            }
        }
    }

    fun setAllTermsChecked(isChecked: Boolean) {
        termsAllCheckState.value = isChecked
        termsOfUseState.value = isChecked
        termsGetInfoState.value = isChecked
        termsUseInfoState.value = isChecked
    }
}
