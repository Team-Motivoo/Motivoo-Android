package sopt.motivoo.data.repository

import sopt.motivoo.data.datasource.remote.OnboardingDataSource
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.request.onboarding.RequestPostInviteCodeDto
import sopt.motivoo.domain.entity.onboarding.FinishedOnboarding
import sopt.motivoo.domain.entity.onboarding.GetMatchedInfo
import sopt.motivoo.domain.entity.onboarding.InviteCodeInfo
import sopt.motivoo.domain.entity.onboarding.MatchedInfo
import sopt.motivoo.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {

    override suspend fun postOnboardingInfo(requestOnboardingDto: RequestOnboardingDto): Result<InviteCodeInfo> =
        runCatching {
            onboardingDataSource.postOnboardingInfo(requestOnboardingDto).toInviteCodeInfo()
        }

    override suspend fun getOnboardingFinished(): Result<FinishedOnboarding> =
        runCatching { onboardingDataSource.getOnboardingFinished().toFinishedOnboarding() }

    override suspend fun patchInviteCode(requestPostInviteCodeDto: RequestPostInviteCodeDto): Result<MatchedInfo> =
        runCatching {
            onboardingDataSource.patchInviteCode(requestPostInviteCodeDto).toMatchedInfo()
        }

    override suspend fun getInviteCode(): Result<GetMatchedInfo> =
        runCatching {
            onboardingDataSource.getInviteCode().toGetMatchedInfo()
        }
}
