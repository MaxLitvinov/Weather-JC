package com.jc.weather.open_weather_map.data.api

import com.jc.weather.api_client.di.factory.network_response.NetworkResponse
import com.jc.weather.open_weather_map.BuildConfig
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("data/2.5/onecall?&lang=en&units=metric")
    suspend fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_MAP_APP_ID
    ): NetworkResponse<WeatherDto, Any>
}
