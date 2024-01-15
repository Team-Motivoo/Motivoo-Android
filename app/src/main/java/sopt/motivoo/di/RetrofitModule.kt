package sopt.motivoo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import sopt.motivoo.BuildConfig.BASE_URL
import sopt.motivoo.BuildConfig.DEBUG
import sopt.motivoo.data.datasource.local.MotivooStorageImpl
import sopt.motivoo.data.datasource.remote.AuthInterceptor
import sopt.motivoo.data.datasource.remote.AuthTokenRefreshListener
import sopt.motivoo.data.datasource.remote.AuthTokenRefreshListenerImpl
import sopt.motivoo.domain.entity.MotivooStorage
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideDataStore(motivooStorageImpl: MotivooStorageImpl): MotivooStorage =
        motivooStorageImpl

    @Provides
    @Singleton
    @Logger
    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor

    @Provides
    @Singleton
    fun provideRefreshListener(authTokenRefreshListenerImpl: AuthTokenRefreshListenerImpl): AuthTokenRefreshListener =
        authTokenRefreshListenerImpl

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @Logger loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            if (DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
                )
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }.build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        ).build()
}
