package com.jc.weather.open_weather_map.domain.repository

import com.jc.weather.open_weather_map.domain.mapper.WeatherDtoMapper
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val api: OpenWeatherMapApi,
    private val mapper: WeatherDtoMapper
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainModel {
        val weatherDto = api.fetchWeather(lat, lon)
        return mapper.mapToDomainModel(weatherDto)
    }
}
