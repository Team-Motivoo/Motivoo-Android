package sopt.motivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.motivoo.data.repository.AuthRepositoryImpl
import sopt.motivoo.data.repository.DummyRepositoryImpl
import sopt.motivoo.data.repository.OnboardingRepositoryImpl
import sopt.motivoo.data.repository.HomeRepositoryImpl
import sopt.motivoo.domain.repository.AuthRepository
import sopt.motivoo.domain.repository.DummyRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.domain.repository.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository =
        dummyRepositoryImpl

    @Provides
    @Singleton
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository =
        homeRepositoryImpl

    @Provides
    @Singleton
    fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun providesOnboardingRepository(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository =
        onboardingRepositoryImpl


    @Provides
    @Singleton
    fun providesStepCountRepository(stepCountRepositoryImpl: StepCountRepositoryImpl): StepCountRepository =
        stepCountRepositoryImpl

    @Provides
    @Singleton
    fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl
}
