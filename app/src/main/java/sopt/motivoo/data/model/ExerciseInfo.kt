package sopt.motivoo.data.model

sealed class ExerciseInfo {
    data class EachDateInfo(
        val date: String,
        val my_mission_img_url: String?,
        val opponent_mission_img_url: String?,
        val my_mission_content: String,
        val my_mission_status: String,
        val opponent_mission_content: String,
        val opponent_mission_status: String,
    ) : ExerciseInfo()

    data class NoticeInfo(
        val mission_content: String,
    ) : ExerciseInfo()
}
