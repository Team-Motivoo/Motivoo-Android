package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<InviteCodeInfo>

    suspend fun getOnboardingFinished(): Result<FinishedOnboarding>
}
