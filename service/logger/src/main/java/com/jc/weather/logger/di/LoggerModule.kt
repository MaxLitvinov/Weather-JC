package com.jc.weather.logger.di

import com.jc.weather.logger.BenchmarkLogger
import com.jc.weather.logger.BuildConfig
import com.jc.weather.logger.Logger
import com.jc.weather.logger.ReleaseLogger
import com.jc.weather.time.TimestampProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoggerModule {

    @Provides
    fun provideLogger(
        timestampProvider: TimestampProvider
    ): Logger = when {
        BuildConfig.DEBUG -> BenchmarkLogger(timestampProvider)
        else -> ReleaseLogger()
    }
}
