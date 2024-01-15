package sopt.motivoo.domain.entity

sealed class ExerciseInfo {
    data class EachDateInfo(
        val userType: String,
        val date: String,
        val myMissionImgUrl: String?,
        val opponentMissionImgUrl: String?,
        val myMissionContent: String,
        val myMissionStatus: String,
        val opponentMissionContent: String,
        val opponentMissionStatus: String,
    ) : ExerciseInfo()

    data class NoticeInfo(
        val missionContent: String,
    ) : ExerciseInfo()
}
