package sopt.motivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityComponent::class)
object CoroutineModule {
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
