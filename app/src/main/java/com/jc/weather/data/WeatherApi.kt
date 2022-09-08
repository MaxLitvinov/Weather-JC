package com.jc.weather.data

import retrofit2.http.GET
import retrofit2.http.Query

private const val APP_ID = "978539e18a215484b0146ed80b932145"

interface WeatherApi {

    @GET("data/2.5/onecall?exclude=minutely,hourly,alerts&lang=en&units=metric")
    suspend fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = APP_ID
    ): WeatherDto
}