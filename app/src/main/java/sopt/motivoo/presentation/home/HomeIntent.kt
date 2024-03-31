package sopt.motivoo.presentation.home

sealed class HomeIntent {
    object FirstSelectMission : HomeIntent()
    object SecondSelectMission : HomeIntent()
}
