package sopt.mottivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.mottivoo.domain.entity.MotivooStorage
import sopt.mottivoo.util.NavigationDecider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationDecider(motivooStorage: MotivooStorage): NavigationDecider =
        NavigationDecider(motivooStorage)
}
