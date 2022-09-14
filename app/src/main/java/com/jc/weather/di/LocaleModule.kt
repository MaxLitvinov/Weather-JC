package com.jc.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object LocaleModule {

    @Provides
    @UserLocale
    fun provideLocale(): Locale = Locale.getDefault()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserLocale
