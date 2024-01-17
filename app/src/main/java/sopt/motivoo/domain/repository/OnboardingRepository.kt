package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding
import sopt.motivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.motivoo.domain.entity.onboarding.MatchedInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<InviteCodeInfo>

    suspend fun getOnboardingFinished(): Result<FinishedOnboarding>

    suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo>

    suspend fun getInviteCode(): Result<GetMatchedInfo>
}
