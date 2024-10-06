package sopt.teammotivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.teammotivoo.data.error.UserErrorHandlerImpl
import sopt.teammotivoo.data.repository.AuthRepositoryImpl
import sopt.teammotivoo.data.repository.DummyRepositoryImpl
import sopt.teammotivoo.data.repository.FirebaseRepositoryImpl
import sopt.teammotivoo.data.repository.HomeRepositoryImpl
import sopt.teammotivoo.data.repository.NetworkRepositoryImpl
import sopt.teammotivoo.data.repository.OnboardingRepositoryImpl
import sopt.teammotivoo.data.repository.StepCountRepositoryImpl
import sopt.teammotivoo.data.repository.UserRepositoryImpl
import sopt.teammotivoo.domain.error.UserErrorHandler
import sopt.teammotivoo.domain.repository.AuthRepository
import sopt.teammotivoo.domain.repository.DummyRepository
import sopt.teammotivoo.domain.repository.FirebaseRepository
import sopt.teammotivoo.domain.repository.HomeRepository
import sopt.teammotivoo.domain.repository.NetworkRepository
import sopt.teammotivoo.domain.repository.OnboardingRepository
import sopt.teammotivoo.domain.repository.StepCountRepository
import sopt.teammotivoo.domain.repository.UserRepository
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
