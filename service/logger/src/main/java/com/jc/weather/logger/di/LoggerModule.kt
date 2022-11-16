package com.jc.weather.logger.di

import com.jc.weather.logger.BuildConfig
import com.jc.weather.logger.DebugLogger
import com.jc.weather.logger.Logger
import com.jc.weather.logger.ReleaseLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoggerModule {

    @Provides
    fun provideLogger(): Logger = when {
        BuildConfig.DEBUG -> DebugLogger()
        else -> ReleaseLogger()
    }
}
