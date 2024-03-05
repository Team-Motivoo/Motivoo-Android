package sopt.motivoo.data.repository

import sopt.motivoo.data.datasource.remote.OnboardingDataSource
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.data.model.response.onboarding.ResponseFinishedOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.motivoo.domain.entity.onboarding.MatchedInfo
import sopt.motivoo.domain.repository.OnboardingRepository
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

    override suspend fun getOnboardingFinished(): Result<ResponseFinishedOnboardingDto> =
        runCatching { onboardingDataSource.getOnboardingFinished() }

    override suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo> =
        runCatching {
            onboardingDataSource.patchInviteCode(requestPostInviteCodeDto).toMatchedInfo()
        }

    override suspend fun getMatchedResult(): Result<GetMatchedInfo> =
        runCatching {
            onboardingDataSource.getMatchedResult().toGetMatchedInfo()
        }
}
