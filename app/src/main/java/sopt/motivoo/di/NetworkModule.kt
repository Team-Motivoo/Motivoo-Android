package sopt.motivoo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sopt.motivoo.util.NetworkStateLiveData
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkStateLiveData(@ApplicationContext context: Context): NetworkStateLiveData {
        return NetworkStateLiveData(context)
    }
}
