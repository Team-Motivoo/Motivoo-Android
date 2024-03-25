package sopt.motivoo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.NavigationDecider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationDecider(motivooStorage: MotivooStorage): NavigationDecider =
        NavigationDecider(motivooStorage)
}
