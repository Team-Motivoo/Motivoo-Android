package sopt.motivoo.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sopt.motivoo.domain.entity.exercise.ExerciseData
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo
import sopt.motivoo.util.extension.prettyString
import java.time.LocalDate

@Serializable
data class ResponseExerciseDto(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: ExerciseHistoryData,
) {
    @Serializable
    data class ExerciseHistoryData(
        @SerialName("user_type") val userType: String,
        @SerialName("today_mission") val todayMission: MissionContent?,
        @SerialName("mission_history") val missionHistory: List<MissionHistory>?,
    ) {
        @Serializable
        data class MissionContent(
            @SerialName("mission_content") val missionContent: String,
            @SerialName("date") val date: String,
        )

        @Serializable
        data class MissionHistory(
            @SerialName("date") val date: String,
            @SerialName("my_mission_content") val myMissionContent: String,
            @SerialName("my_mission_img_url") val myMissionImgUrl: String?,
            @SerialName("my_mission_status") val myMissionStatus: String,
            @SerialName("opponent_mission_content") val opponentMissionContent: String,
            @SerialName("opponent_mission_img_url") val opponentMissionImgUrl: String?,
            @SerialName("opponent_mission_status") val opponentMissionStatus: String,
        )
    }

    fun toExerciseData(): ExerciseData {
        val list: MutableList<ExerciseItemInfo> =
            if (data.todayMission == null && data.missionHistory?.isEmpty() == true) {
                mutableListOf()
            } else if (data.todayMission == null) {
                mutableListOf(
                    ExerciseItemInfo.NoticeItemInfo(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                )
            } else {
                mutableListOf(
                    ExerciseItemInfo.NoticeItemInfo(
                        data.todayMission?.missionContent,
                        data.missionHistory!![0].myMissionStatus,
                        data.missionHistory[0]!!.opponentMissionStatus,
                        data.todayMission.date, data.missionHistory[0]!!.date,
                        data.missionHistory[0]!!.myMissionImgUrl,
                        data.missionHistory[0]!!.opponentMissionImgUrl,
                    )
                )
            }

        data.missionHistory?.forEach {
            fun String.removeDayOfTheWeek(): String = this.removeRange(length - 4 until length)
            if (it.date.removeDayOfTheWeek() != LocalDate.now().prettyString) {
                list.add(
                    ExerciseItemInfo.EachDateItemInfo(
                        date = it.date,
                        myMissionContent = it.myMissionContent,
                        myMissionImgUrl = it.myMissionImgUrl,
                        myMissionStatus = it.myMissionStatus,
                        opponentMissionContent = it.opponentMissionContent,
                        opponentMissionImgUrl = it.opponentMissionImgUrl,
                        opponentMissionStatus = it.opponentMissionStatus
                    )
                )
            }
        }
        return ExerciseData(data.userType, list)
    }
}
