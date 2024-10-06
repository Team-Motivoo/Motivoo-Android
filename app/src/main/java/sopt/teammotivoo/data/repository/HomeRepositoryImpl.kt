package sopt.teammotivoo.data.repository

import android.graphics.Bitmap
import sopt.teammotivoo.data.datasource.remote.HomeDataSource
import sopt.teammotivoo.data.model.request.home.RequestMissionTodayDto
import sopt.teammotivoo.domain.entity.error.ResponseHandler
import sopt.teammotivoo.domain.entity.home.HomeData
import sopt.teammotivoo.domain.entity.home.MissionChoiceData
import sopt.teammotivoo.domain.entity.home.MissionImageData
import sopt.teammotivoo.domain.error.UserErrorHandler
import sopt.teammotivoo.domain.repository.HomeRepository
import sopt.teammotivoo.util.BitmapRequestBody
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
        val requestBody = BitmapRequestBody(bitmap).create(50)
        homeDataSource.uploadPhoto(url, requestBody)
    } catch (e: Exception) {
        null
    }
}
