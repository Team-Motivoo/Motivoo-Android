package sopt.motivoo.data.model.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionChoiceListData
import sopt.motivoo.domain.entity.home.TodayMissionData

@Serializable
data class ResponseMissionChoiceDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ResponseMissionChoiceDataDto,
) {
    @Serializable
    data class ResponseMissionChoiceDataDto(
        @SerialName("is_choice_finished")
        val isChoiceFinished: Boolean,
        @SerialName("date")
        val date: String?,
        @SerialName("mission_choice_list")
        val missionChoiceList: List<ResponseMissionChoiceListDto>?,
        @SerialName("today_mission")
        val todayMission: ResponseTodayMissionDto?,
    ) {
        @Serializable
        data class ResponseMissionChoiceListDto(
            @SerialName("mission_id")
            val missionId: Long,
            @SerialName("mission_content")
            val missionContent: String,
            @SerialName("mission_icon_url")
            val missionIconUrl: String,
        ) {
            fun toMissionChoiceListData(): MissionChoiceListData = MissionChoiceListData(
                missionId = missionId,
                missionContent = missionContent,
                missionIconUrl = missionIconUrl
            )
        }

        @Serializable
        data class ResponseTodayMissionDto(
            @SerialName("mission_content")
            val missionContent: String,
            @SerialName("mission_description")
            val missionDescription: String,
            @SerialName("mission_step_count")
            val missionStepCount: Int,
            @SerialName("mission_quest")
            val missionQuest: String,
        ) {
            fun toTodayMissionData(): TodayMissionData = TodayMissionData(
                missionContent = missionContent,
                missionDescription = missionDescription,
                missionStepCount = missionStepCount,
                missionQuest = missionQuest
            )
        }
    }

    fun toMissionChoiceData(): MissionChoiceData = MissionChoiceData(
        code = code,
        isChoiceFinished = data.isChoiceFinished,
        date = data.date ?: "",
        missionChoiceList = data.missionChoiceList?.map {
            it.toMissionChoiceListData()
        }.orEmpty(),
        todayMission = data.todayMission?.toTodayMissionData() ?: TodayMissionData()
    )
}
