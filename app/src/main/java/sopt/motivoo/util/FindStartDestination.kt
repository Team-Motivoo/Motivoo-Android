package sopt.motivoo.util

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph

fun NavController.findStartDestination(): NavDestination {
    var startDestination: NavDestination = graph
    while (startDestination is NavGraph) {
        val graphStartDestination = startDestination
        startDestination = graphStartDestination.findNode(graphStartDestination.startDestinationId)
            ?: throw IllegalStateException("Failed Found Start Destination")
    }
    return startDestination
}
