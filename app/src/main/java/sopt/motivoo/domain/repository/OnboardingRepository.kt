package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.motivoo.domain.entity.onboarding.MatchedInfoInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<InviteCodeInfo>

    suspend fun getOnboardingFinished(): Result<FinishedOnboarding>

    suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfoInfo>
}
