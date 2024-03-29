package sopt.motivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.motivoo.data.error.UserErrorHandlerImpl
import sopt.motivoo.data.repository.AuthRepositoryImpl
import sopt.motivoo.data.repository.DummyRepositoryImpl
import sopt.motivoo.data.repository.FirebaseRepositoryImpl
import sopt.motivoo.data.repository.HomeRepositoryImpl
import sopt.motivoo.data.repository.NetworkRepositoryImpl
import sopt.motivoo.data.repository.OnboardingRepositoryImpl
import sopt.motivoo.data.repository.StepCountRepositoryImpl
import sopt.motivoo.data.repository.UserRepositoryImpl
import sopt.motivoo.domain.error.UserErrorHandler
import sopt.motivoo.domain.repository.AuthRepository
import sopt.motivoo.domain.repository.DummyRepository
import sopt.motivoo.domain.repository.FirebaseRepository
import sopt.motivoo.domain.repository.HomeRepository
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.domain.repository.OnboardingRepository
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.domain.repository.UserRepository
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
    fun providesFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository =
        firebaseRepositoryImpl

    @Provides
    @Singleton
    fun providesStepCountRepository(stepCountRepositoryImpl: StepCountRepositoryImpl): StepCountRepository =
        stepCountRepositoryImpl

    @Provides
    @Singleton
    fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    @Singleton
    fun providesNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository =
        networkRepositoryImpl

    @Provides
    @Singleton
    fun providesUserErrorHandler(userErrorHandlerImpl: UserErrorHandlerImpl): UserErrorHandler =
        userErrorHandlerImpl
}
