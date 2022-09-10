package com.jc.weather.pages.home.core

import com.jc.weather.data.OpenWeatherMapApi
import com.jc.weather.domain.model.WeatherDomainModel
import com.jc.weather.domain.mapper.WeatherDtoMapper
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