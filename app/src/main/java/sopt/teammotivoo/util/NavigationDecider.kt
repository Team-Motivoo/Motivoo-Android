package sopt.teammotivoo.util

import sopt.teammotivoo.domain.entity.MotivooStorage
import javax.inject.Inject

class NavigationDecider @Inject constructor(
    private val motivooStorage: MotivooStorage
) {
    fun determineNavigationDestination(): NavigationEvent {
        return when {
            motivooStorage.isUserMatched ->
                NavigationEvent.Home

            !motivooStorage.isUserMatched && motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn ->
                NavigationEvent.StartMotivoo

            !motivooStorage.isUserMatched && !motivooStorage.isFinishedOnboarding && motivooStorage.isUserLoggedIn && motivooStorage.isFinishedTermsOfUse && motivooStorage.isFinishedPermission ->
                NavigationEvent.Description

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
    data object Description : NavigationEvent()
    data object StartMotivoo : NavigationEvent()
    data object Login : NavigationEvent()
    data object Permission : NavigationEvent()
    data object TermsOfUse : NavigationEvent()
    data object Init : NavigationEvent()
}
