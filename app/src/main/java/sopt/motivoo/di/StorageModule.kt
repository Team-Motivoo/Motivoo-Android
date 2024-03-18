package sopt.motivoo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sopt.motivoo.data.datasource.local.MotivooStorageImpl
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideMotivooStorage(@ApplicationContext context: Context): MotivooStorage =
        MotivooStorageImpl(context)
}
