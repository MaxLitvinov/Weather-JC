package com.jc.weather.data.open_weather_map

import com.jc.weather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("data/2.5/onecall?exclude=minutely,hourly,alerts&lang=en&units=metric")
    suspend fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_MAP_APP_ID
    ): WeatherDto
}
