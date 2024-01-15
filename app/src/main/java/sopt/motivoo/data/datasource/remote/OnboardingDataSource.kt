package sopt.motivoo.data.datasource.remote

import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.data.service.OnboardingService
import javax.inject.Inject

class OnboardingDataSource @Inject constructor(
    private val onboardingService: OnboardingService,
) {
    suspend fun postOnboardingInfo(
        requestOnboardingDto: RequestOnboardingDto
    ): ResponseOnboardingDto =
        onboardingService.postOnboardingInfo(requestOnboardingDto)
}
