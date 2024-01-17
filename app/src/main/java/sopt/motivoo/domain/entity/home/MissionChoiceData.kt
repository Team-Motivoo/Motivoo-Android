package sopt.motivoo.domain.entity.home

data class MissionChoiceData(
    val isChoiceFinished: Boolean,
    val date: String,
    val missionChoiceList: List<MissionChoiceListData>,
    val todayMission: TodayMissionData,
)