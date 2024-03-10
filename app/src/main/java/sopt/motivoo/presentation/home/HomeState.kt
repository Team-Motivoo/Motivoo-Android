package sopt.motivoo.presentation.home

import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceListData
import sopt.motivoo.domain.entity.home.TodayMissionData

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class FetchHomeData(
        val homeData: HomeData,
    ) : HomeState()

    data class SelectedMission(
        val missionId: Int,
    ) : HomeState()

    data class SelectedMissionData(
        val todayMission: TodayMissionData,
    ) : HomeState()

    data class UnSelectedMissionData(
        val missionChoiceList: List<MissionChoiceListData>,
        val date: String,
    ) : HomeState()

    object CompletedStepCount : HomeState()
    object CompletedMission : HomeState()
    object HighFive : HomeState()
    object FailMatching : HomeState()
    object Confirm : HomeState()
}
