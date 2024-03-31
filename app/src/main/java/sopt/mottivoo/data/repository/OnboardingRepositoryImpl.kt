package sopt.mottivoo.data.repository

import sopt.mottivoo.data.datasource.remote.OnboardingDataSource
import sopt.mottivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.mottivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.mottivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.mottivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.mottivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.mottivoo.domain.entity.onboarding.MatchedInfo
import sopt.mottivoo.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {

    override suspend fun postOnboardingInfo(requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto> =
        runCatching {
            onboardingDataSource.postOnboardingInfo(requestOnboardingDto)
        }

    override suspend fun getInviteCode(): Result<InviteCodeInfo> =
        runCatching { onboardingDataSource.getInviteCode().toGetInviteCode() }

    override suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo> =
        runCatching {
            onboardingDataSource.patchInviteCode(requestPostInviteCodeDto).toMatchedInfo()
        }

    override suspend fun getMatchedResult(): Result<GetMatchedInfo> =
        runCatching {
            onboardingDataSource.getMatchedResult().toGetMatchedInfo()
        }
}
