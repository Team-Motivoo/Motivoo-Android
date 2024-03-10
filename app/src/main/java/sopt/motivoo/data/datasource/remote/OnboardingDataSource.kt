package sopt.motivoo.data.datasource.remote

import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseFinishedOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseGetInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseGetMatchedResultDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponsePostInviteCodeDto
import sopt.motivoo.data.service.OnboardingService
import javax.inject.Inject

class OnboardingDataSource @Inject constructor(
    private val onboardingService: OnboardingService,
) {
    suspend fun postOnboardingInfo(
        requestOnboardingDto: RequestOnboardingDto
    ): ResponseOnboardingDto =
        onboardingService.postOnboardingInfo(requestOnboardingDto)

    suspend fun getInviteCode(): ResponseGetInviteCodeDto =
        onboardingService.getInviteCode()

    suspend fun getOnboardingFinished(): ResponseFinishedOnboardingDto =
        onboardingService.getOnboardingFinished()

    suspend fun patchInviteCode(
        requestPostInviteCodeDto: RequestPostInviteCodeDto
    ): ResponsePostInviteCodeDto =
        onboardingService.patchInviteCode(requestPostInviteCodeDto)

    suspend fun getMatchedResult(): ResponseGetMatchedResultDto =
        onboardingService.getMatchedResult()
}
