package sopt.motivoo.domain.entity.home

data class MissionChoiceData(
    val code: Int,
    val isChoiceFinished: Boolean,
    val date: String,
    val missionChoiceList: List<MissionChoiceListData>,
    val todayMission: TodayMissionData,
)
