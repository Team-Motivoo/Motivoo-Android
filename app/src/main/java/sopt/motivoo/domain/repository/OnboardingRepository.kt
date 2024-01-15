package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto>

    suspend fun getOnboardingFinished(): Result<FinishedOnboarding>
}
