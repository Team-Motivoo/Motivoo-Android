package sopt.motivoo.domain.repository

import android.graphics.Bitmap
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData

interface HomeRepository {
    suspend fun patchHome(myStepCount: Int, otherStepCount: Int): HomeData?
    suspend fun postMissionTodayChoice(): MissionChoiceData?
    suspend fun postMissionToday(missionId: Int): Unit?
    suspend fun getMissionImage(imagePrefix: String): MissionImageData?
    suspend fun patchMissionImage(fileName: String): Unit?
    suspend fun uploadPhoto(url: String, bitmap: Bitmap): Unit?
}
