package sopt.teammotivoo.domain.repository

import android.graphics.Bitmap
import sopt.teammotivoo.domain.entity.error.ResponseHandler
import sopt.teammotivoo.domain.entity.home.HomeData
import sopt.teammotivoo.domain.entity.home.MissionChoiceData
import sopt.teammotivoo.domain.entity.home.MissionImageData

interface HomeRepository {
    suspend fun patchHome(): HomeData?
    suspend fun postMissionTodayChoice(): ResponseHandler<MissionChoiceData?>
    suspend fun postMissionToday(missionId: Int): Unit?
    suspend fun getMissionImage(imagePrefix: String): MissionImageData?
    suspend fun patchMissionImage(fileName: String): Unit?
    suspend fun uploadPhoto(url: String, bitmap: Bitmap): Unit?
}
