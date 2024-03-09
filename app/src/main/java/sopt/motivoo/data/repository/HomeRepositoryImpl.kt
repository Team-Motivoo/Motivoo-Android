package sopt.motivoo.data.repository

import android.graphics.Bitmap
import okhttp3.RequestBody.Companion.toRequestBody
import sopt.motivoo.data.datasource.remote.HomeDataSource
import sopt.motivoo.data.model.request.home.RequestHomeDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData
import sopt.motivoo.domain.repository.HomeRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun patchHome(myStepCount: Int, otherStepCount: Int): HomeData? =
        try {
            homeDataSource.patchHome(RequestHomeDto(myStepCount, otherStepCount)).toHomeData()
        } catch (e: Exception) {
            null
        }

    override suspend fun postMissionTodayChoice(): MissionChoiceData? =
        try {
            homeDataSource.postMissionTodayChoice().toMissionChoiceData()
        } catch (e: Exception) {
            null
        }

    override suspend fun postMissionToday(missionId: Int): Unit? =
        try {
            if (homeDataSource.postMissionToday(RequestMissionTodayDto(missionId)).success) Unit else null
        } catch (e: Exception) {
            null
        }

    override suspend fun getMissionImage(imagePrefix: String): MissionImageData? =
        try {
            homeDataSource.getMissionImage(imagePrefix).toMissionImageData()
        } catch (e: Exception) {
            null
        }


    override suspend fun patchMissionImage(fileName: String): Unit? = try {
        homeDataSource.patchMissionImage(fileName)
        Unit
    } catch (e: Exception) {
        null
    }

    override suspend fun uploadPhoto(url: String, bitmap: Bitmap): Unit? = try {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
        val requestBody = outputStream.toByteArray().toRequestBody()
        homeDataSource.uploadPhoto(url, requestBody)
    } catch (e: Exception) {
        null
    }
}
