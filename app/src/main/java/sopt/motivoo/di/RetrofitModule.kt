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
import sopt.motivoo.data.datasource.remote.intercepter.AuthInterceptor
import sopt.motivoo.data.datasource.remote.intercepter.ErrorInterceptor
import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListener
import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListenerImpl
import sopt.motivoo.data.datasource.remote.listener.NetworkErrorListener
import sopt.motivoo.data.datasource.remote.listener.NetworkErrorListenerImpl
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
    @AuthInterceptorQualifier
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    fun provideRefreshListener(authTokenRefreshListenerImpl: AuthTokenRefreshListenerImpl): AuthTokenRefreshListener =
        authTokenRefreshListenerImpl

    @Provides
    @Singleton
    @ErrorInterceptorQualifier
    fun provideErrorInterceptor(errorInterceptor: ErrorInterceptor): Interceptor = errorInterceptor

    @Provides
    @Singleton
    fun provideNetworkErrorListener(networkErrorListenerImpl: NetworkErrorListenerImpl): NetworkErrorListener =
        networkErrorListenerImpl

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

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
        loggingInterceptor: HttpLoggingInterceptor,
        @AuthInterceptorQualifier authInterceptor: Interceptor,
        @ErrorInterceptorQualifier errorInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(authInterceptor)
            addInterceptor(errorInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        ).build()
}
