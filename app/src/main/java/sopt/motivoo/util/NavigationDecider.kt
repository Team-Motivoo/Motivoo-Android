package sopt.motivoo.util

import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

class NavigationDecider @Inject constructor(
    private val motivooStorage: MotivooStorage
) {
    fun determineNavigationDestination(): NavigationEvent {
        return when {
            !motivooStorage.isFinishedPermission && motivooStorage.isUserLoggedIn ->
                NavigationEvent.Permission

            !motivooStorage.isFinishedTermsOfUse && motivooStorage.isUserLoggedIn ->
                NavigationEvent.TermsOfUse

            !motivooStorage.isUserMatched && !motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn ->
                NavigationEvent.AgeQuestion

            !motivooStorage.isUserMatched && motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn ->
                NavigationEvent.StartMotivoo

            motivooStorage.isUserMatched && motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn ->
                NavigationEvent.Home

            else ->
                NavigationEvent.Login
        }
    }
}

sealed class NavigationEvent {
    data object Home : NavigationEvent()
    data object AgeQuestion : NavigationEvent()
    data object StartMotivoo : NavigationEvent()
    data object Login : NavigationEvent()
    data object Permission : NavigationEvent()
    data object TermsOfUse : NavigationEvent()
    data object Init : NavigationEvent()
}
