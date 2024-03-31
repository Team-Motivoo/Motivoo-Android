package sopt.mottivoo.data.datasource.remote

import sopt.mottivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.mottivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.mottivoo.data.model.response.onboarding.ResponseGetInviteCodeDto
import sopt.mottivoo.data.model.response.onboarding.ResponseGetMatchedResultDto
import sopt.mottivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.mottivoo.data.model.response.onboarding.ResponsePostInviteCodeDto
import sopt.mottivoo.data.service.OnboardingService
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

    suspend fun patchInviteCode(
        requestPostInviteCodeDto: RequestPostInviteCodeDto
    ): ResponsePostInviteCodeDto =
        onboardingService.patchInviteCode(requestPostInviteCodeDto)

    suspend fun getMatchedResult(): ResponseGetMatchedResultDto =
        onboardingService.getMatchedResult()
}
