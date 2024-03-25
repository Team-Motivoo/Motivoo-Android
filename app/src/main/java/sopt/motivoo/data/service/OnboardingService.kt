package sopt.motivoo.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseGetInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseGetMatchedResultDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponsePostInviteCodeDto

interface OnboardingService {
    @POST("user/exercise")
    suspend fun postOnboardingInfo(
        @Body requestOnboardingDto: RequestOnboardingDto,
    ): ResponseOnboardingDto

    @POST("parentchild/invite")
    suspend fun getInviteCode(): ResponseGetInviteCodeDto

    @PATCH("parentchild/match")
    suspend fun patchInviteCode(
        @Body requestPostInviteCodeDto: RequestPostInviteCodeDto
    ): ResponsePostInviteCodeDto

    @GET("onboarding/match")
    suspend fun getMatchedResult(): ResponseGetMatchedResultDto
}
