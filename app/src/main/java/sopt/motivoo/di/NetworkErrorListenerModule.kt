//package sopt.motivoo.di
//
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityComponent
//import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListener
//import sopt.motivoo.data.datasource.remote.listener.AuthTokenRefreshListenerImpl
//
//@Module
//@InstallIn(ActivityComponent::class) // 또는 적절한 스코프
//abstract class AuthTokenRefreshListenerModule {
//
//    @Binds
//    abstract fun bindAuthTokenRefreshListener(
//        authTokenRefreshListenerImpl: AuthTokenRefreshListenerImpl
//    ): AuthTokenRefreshListener
//
//}
