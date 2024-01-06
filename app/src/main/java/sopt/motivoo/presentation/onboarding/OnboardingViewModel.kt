package sopt.motivoo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sopt.motivoo.presentation.type.UserType

class OnboardingViewModel : ViewModel() {

    private val _userType = MutableStateFlow<UserType?>(null)
    val userType get() = _userType.asStateFlow()

    val age = MutableStateFlow<String?>(null)

    val isValidAge: StateFlow<Boolean?> = age.map { ageString ->
        when {
            ageString.isNullOrEmpty() -> null
            ageString.toIntOrNull()?.let { it in 14..99 } == true -> true
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val isValidNext: StateFlow<Boolean> =
        combine(
            userType,
            isValidAge,
        ) { userType, isValidAge ->
            userType != null && isValidAge == true
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


//    val userFilterType: MutableStateFlow<Map<UserType, Boolean>> = MutableStateFlow(
//        mapOf(
//            UserType.PARENT to false,
//            UserType.CHILD to false,
//        ),
//    )

//    fun setUserFilterType(userType: UserType) {
//        val isSelected = userFilterType.value[userType] ?: return
//        userFilterType.value = userFilterType.value.toMutableMap().apply {
//            this[userType] = !isSelected
//        }
//    }

    fun setUserType(userType: UserType) {
        _userType.value = userType
    }

}