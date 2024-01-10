package sopt.motivoo.presentation.invitecode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class InviteCodeViewModel : ViewModel() {

    val postInviteCode = MutableStateFlow<String?>(null)

    val isValidCode: StateFlow<Boolean?> = postInviteCode.map { codeString ->
        when {
            codeString.isNullOrEmpty() -> null
            codeString.trim().isNotEmpty() -> true
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}
