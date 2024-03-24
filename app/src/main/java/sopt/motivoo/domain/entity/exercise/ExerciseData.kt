package sopt.motivoo.domain.entity.exercise

class ExerciseData(
    val userType: String,
    val exerciseItemInfoList: List<ExerciseItemInfo>,
) {
    sealed class ExerciseItemInfo {
        data class EachDateItemInfo(
            val date: String,
            val myMissionImgUrl: String?,
            val opponentMissionImgUrl: String?,
            val myMissionContent: String,
            val myMissionStatus: String,
            val opponentMissionContent: String,
            val opponentMissionStatus: String,
        ) : ExerciseItemInfo()

        data class NoticeItemInfo(
            val missionContent: String?,
            val myMissionStatus: String?,
            val opponentMissionStatus: String?,
            val todayDate: String?,
            val missionDate: String?,
            val myMissionImgUrl: String?,
            val opponentMissionImgUrl: String?,
        ) : ExerciseItemInfo()
    }
}
