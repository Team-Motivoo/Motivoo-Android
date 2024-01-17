package sopt.motivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import sopt.motivoo.data.service.AuthService
import sopt.motivoo.data.service.DummyService
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.data.service.MyPageService
import sopt.motivoo.data.service.OnboardingService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private inline fun <reified T> Retrofit.create(): T = this.create(T::class.java)

    @Provides
    @Singleton
    fun provideDummyService(retrofit: Retrofit): DummyService = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun provideOnboardingService(retrofit: Retrofit): OnboardingService = retrofit.create()

    @Provides
    @Singleton
    fun provideMyPageService(retrofit: Retrofit): MyPageService = retrofit.create()

    @Provides
    @Singleton
    fun provideExerciseService(retrofit: Retrofit): ExerciseService = retrofit.create()
}
