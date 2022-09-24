package com.jc.weather.open_weather_map.di

import com.jc.weather.open_weather_map.BuildConfig
import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class OpenWeatherMapModule {

    @Provides
    fun provideWeatherApi(retrofitBuilder: Retrofit.Builder): OpenWeatherMapApi =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_OPEN_WEATHER_MAP)
            .build()
            .create(OpenWeatherMapApi::class.java)
}
