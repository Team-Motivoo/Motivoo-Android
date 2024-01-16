package sopt.motivoo.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseExerciseEachDateInfo(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ExerciseHistoryData,
) {
    @Serializable
    data class ExerciseHistoryData(
        @SerialName("user_type")
        val userType: String,
        @SerialName("today_mission")
        val todayMission: MissionContent,
        @SerialName("mission_history")
        val missionHistory: List<MissionHistory>,
    ) {
        @Serializable
        data class MissionContent(
            @SerialName("mission_content")
            val missionContent: String,
        )

        @Serializable
        data class MissionHistory(
            @SerialName("date")
            val date: String,
            @SerialName("my_mission_content")
            val myMissionContent: String,
            @SerialName("my_mission_img_url")
            val myMissionImgUrl: String,
            @SerialName("my_mission_status")
            val myMissionStatus: String,
            @SerialName("opponent_mission_content")
            val opponentMissionContent: String,
            @SerialName("opponent_mission_img_url")
            val opponentMissionImgUrl: String,
            @SerialName("opponent_mission_status")
            val opponentMissionStatus: String,
        )
    }
}




