package sopt.mottivoo.domain.repository

import retrofit2.http.Body
import sopt.mottivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.mottivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.mottivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.mottivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.mottivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.mottivoo.domain.entity.onboarding.MatchedInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto>

    suspend fun getInviteCode(): Result<InviteCodeInfo>

    suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo>

    suspend fun getMatchedResult(): Result<GetMatchedInfo>
}
