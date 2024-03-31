package sopt.mottivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import sopt.mottivoo.data.service.AuthService
import sopt.mottivoo.data.service.DummyService
import sopt.mottivoo.data.service.ExerciseService
import sopt.mottivoo.data.service.HomeService
import sopt.mottivoo.data.service.MyExerciseInfoService
import sopt.mottivoo.data.service.MyPageService
import sopt.mottivoo.data.service.OnboardingService
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

    @Provides
    @Singleton
    fun provideMyExerciseInfoService(retrofit: Retrofit): MyExerciseInfoService = retrofit.create()

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create()
}
