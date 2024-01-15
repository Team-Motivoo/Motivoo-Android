package sopt.motivoo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import sopt.motivoo.data.service.KakaoAuthService

@Module
@InstallIn(ActivityComponent::class)
object KakaoModule {
    @Provides
    fun provideKakaoAuthService(
        @ActivityContext context: Context,
    ) = KakaoAuthService(context)
}
