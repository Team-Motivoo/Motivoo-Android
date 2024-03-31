package sopt.mottivoo.presentation.home

sealed class HomeIntent {
    object FirstSelectMission : HomeIntent()
    object SecondSelectMission : HomeIntent()
}
