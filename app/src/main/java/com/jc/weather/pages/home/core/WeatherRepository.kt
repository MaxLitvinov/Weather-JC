package com.jc.weather.pages.home.core

import com.jc.weather.data.open_weather_map.OpenWeatherMapApi
import com.jc.weather.domain.open_weather_map.mapper.WeatherDtoMapper
import com.jc.weather.domain.open_weather_map.model.WeatherDomainModel
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: OpenWeatherMapApi,
    private val mapper: WeatherDtoMapper
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainModel {
        val weatherDto = api.fetchWeather(lat, lon)
        return mapper.mapToDomainModel(weatherDto)
    }
}
