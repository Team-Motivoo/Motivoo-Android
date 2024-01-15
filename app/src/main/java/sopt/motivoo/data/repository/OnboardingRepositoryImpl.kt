package sopt.motivoo.data.repository

import sopt.motivoo.data.datasource.remote.OnboardingDataSource
import sopt.motivoo.data.model.request.onboarding.RequestOnboardingDto
import sopt.motivoo.data.model.response.onboarding.ResponseOnboardingDto
import sopt.motivoo.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataSource: OnboardingDataSource,
) : OnboardingRepository {

    override suspend fun postOnboardingInfo(requestOnboardingDto: RequestOnboardingDto): Result<ResponseOnboardingDto> =
        runCatching { onboardingDataSource.postOnboardingInfo(requestOnboardingDto) }
}
