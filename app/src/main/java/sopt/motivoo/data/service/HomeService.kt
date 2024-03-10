package sopt.motivoo.data.service

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url
import sopt.motivoo.data.model.request.home.RequestMissionImageFileDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.data.model.response.home.ResponseHomeDto
import sopt.motivoo.data.model.response.home.ResponseMissionChoiceDto
import sopt.motivoo.data.model.response.home.ResponseMissionImageDto
import sopt.motivoo.data.model.response.home.ResponseMissionTodayDto

interface HomeService {
    @GET("home")
    suspend fun patchHome(): ResponseHomeDto

    @POST("mission/today/choice")
    suspend fun postMissionTodayChoice(): ResponseMissionChoiceDto

    @POST("mission/today")
    suspend fun postMissionToday(
        @Body requestMissionTodayDto: RequestMissionTodayDto,
    ): ResponseMissionTodayDto

    @GET("mission/image/{img_prefix}")
    suspend fun getMissionImage(
        @Path("img_prefix") imgPrefix: String,
    ): ResponseMissionImageDto

    @PATCH("mission/image")
    suspend fun patchMissionImage(
        @Body requestMissionImageFileDto: RequestMissionImageFileDto,
    ): ResponseMissionTodayDto

    @PUT
    suspend fun uploadPhoto(
        @Url imgPresignedUrl: String,
        @Body photo: RequestBody,
    ): Response<Unit>
}
