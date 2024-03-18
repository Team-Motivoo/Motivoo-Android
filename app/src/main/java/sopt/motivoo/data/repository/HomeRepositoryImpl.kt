package sopt.motivoo.data.repository

import android.graphics.Bitmap
import okhttp3.RequestBody.Companion.toRequestBody
import sopt.motivoo.data.datasource.remote.HomeDataSource
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.domain.entity.error.ResponseHandler
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData
import sopt.motivoo.domain.error.UserErrorHandler
import sopt.motivoo.domain.repository.HomeRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
    private val userErrorHandler: UserErrorHandler,
) : HomeRepository {
    override suspend fun patchHome(): HomeData? =
        try {
            homeDataSource.patchHome().toHomeData()
        } catch (e: Exception) {
            null
        }

    override suspend fun postMissionTodayChoice(): ResponseHandler<MissionChoiceData?> = try {
        val missionChoiceData = homeDataSource.postMissionTodayChoice().toMissionChoiceData()
        ResponseHandler(code = missionChoiceData.code, data = missionChoiceData)
    } catch (e: Exception) {
        userErrorHandler.handleUserError(throwable = e, null)
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
