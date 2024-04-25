package sopt.motivoo.util

import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

class NavigationDecider @Inject constructor(
    private val motivooStorage: MotivooStorage
) {
    fun determineNavigationDestination(): NavigationEvent {
        return when {
            motivooStorage.isUserMatched ->
                NavigationEvent.Home

            !motivooStorage.isUserMatched && motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn && motivooStorage.isFinishedTermsOfUse && motivooStorage.isFinishedPermission ->
                NavigationEvent.StartMotivoo

            !motivooStorage.isUserMatched && !motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn && motivooStorage.isFinishedTermsOfUse && motivooStorage.isFinishedPermission ->
                NavigationEvent.NickName

            !motivooStorage.isFinishedTermsOfUse && motivooStorage.isFinishedPermission && motivooStorage.isUserLoggedIn ->
                NavigationEvent.TermsOfUse

            !motivooStorage.isFinishedPermission && motivooStorage.isUserLoggedIn ->
                NavigationEvent.Permission

            else ->
                NavigationEvent.Login
        }
    }
}

sealed class NavigationEvent {
    data object Home : NavigationEvent()
    data object NickName : NavigationEvent()
    data object StartMotivoo : NavigationEvent()
    data object Login : NavigationEvent()
    data object Permission : NavigationEvent()
    data object TermsOfUse : NavigationEvent()
    data object Init : NavigationEvent()
}
