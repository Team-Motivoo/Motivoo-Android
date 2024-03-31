package sopt.mottivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.mottivoo.data.error.UserErrorHandlerImpl
import sopt.mottivoo.data.repository.AuthRepositoryImpl
import sopt.mottivoo.data.repository.DummyRepositoryImpl
import sopt.mottivoo.data.repository.FirebaseRepositoryImpl
import sopt.mottivoo.data.repository.HomeRepositoryImpl
import sopt.mottivoo.data.repository.NetworkRepositoryImpl
import sopt.mottivoo.data.repository.OnboardingRepositoryImpl
import sopt.mottivoo.data.repository.StepCountRepositoryImpl
import sopt.mottivoo.data.repository.UserRepositoryImpl
import sopt.mottivoo.domain.error.UserErrorHandler
import sopt.mottivoo.domain.repository.AuthRepository
import sopt.mottivoo.domain.repository.DummyRepository
import sopt.mottivoo.domain.repository.FirebaseRepository
import sopt.mottivoo.domain.repository.HomeRepository
import sopt.mottivoo.domain.repository.NetworkRepository
import sopt.mottivoo.domain.repository.OnboardingRepository
import sopt.mottivoo.domain.repository.StepCountRepository
import sopt.mottivoo.domain.repository.UserRepository
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
