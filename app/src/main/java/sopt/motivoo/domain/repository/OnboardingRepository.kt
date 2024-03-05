package sopt.motivoo.domain.repository

import retrofit2.http.Body
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseFinishedOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.motivoo.domain.entity.onboarding.MatchedInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto>

    suspend fun getInviteCode(): Result<InviteCodeInfo>

    suspend fun getOnboardingFinished(): Result<ResponseFinishedOnboardingDto>

    suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo>

    suspend fun getMatchedResult(): Result<GetMatchedInfo>
}
