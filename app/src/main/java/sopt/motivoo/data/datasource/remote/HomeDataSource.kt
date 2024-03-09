package sopt.motivoo.data.datasource.remote

import okhttp3.RequestBody
import sopt.motivoo.data.model.request.home.RequestHomeDto
import sopt.motivoo.data.model.request.home.RequestMissionImageFileDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.data.model.response.home.ResponseHomeDto
import sopt.motivoo.data.model.response.home.ResponseMissionChoiceDto
import sopt.motivoo.data.model.response.home.ResponseMissionImageDto
import sopt.motivoo.data.model.response.home.ResponseMissionTodayDto
import sopt.motivoo.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun postMissionTodayChoice(): ResponseMissionChoiceDto =
        homeService.postMissionTodayChoice()

    suspend fun patchHome(requestHomeDto: RequestHomeDto): ResponseHomeDto =
        homeService.patchHome(requestHomeDto)

    suspend fun postMissionToday(requestMissionTodayDto: RequestMissionTodayDto): ResponseMissionTodayDto =
        homeService.postMissionToday(requestMissionTodayDto)

    suspend fun getMissionImage(imagePrefix: String): ResponseMissionImageDto =
        homeService.getMissionImage(imagePrefix)

    suspend fun patchMissionImage(fileName: String): ResponseMissionTodayDto =
        homeService.patchMissionImage(RequestMissionImageFileDto(fileName))

    suspend fun uploadPhoto(url: String, photo: RequestBody) {
        homeService.uploadPhoto(url, photo)
    }
}
