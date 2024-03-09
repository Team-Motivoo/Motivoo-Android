package sopt.motivoo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ErrorInterceptorQualifier
