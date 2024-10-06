package sopt.teammotivoo.domain.repository

import retrofit2.http.Body
import sopt.teammotivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.teammotivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.teammotivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.teammotivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.teammotivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.teammotivoo.domain.entity.onboarding.MatchedInfo

interface OnboardingRepository {

    suspend fun postOnboardingInfo(@Body requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto>

    suspend fun getInviteCode(): Result<InviteCodeInfo>

    suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo>

    suspend fun getMatchedResult(): Result<GetMatchedInfo>
}
