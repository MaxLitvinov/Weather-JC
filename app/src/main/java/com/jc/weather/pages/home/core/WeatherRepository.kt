package com.jc.weather.pages.home.core

import com.jc.weather.data.WeatherApi
import com.jc.weather.domain.model.WeatherDomainModel
import com.jc.weather.domain.mapper.WeatherDtoMapper
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDtoMapper: WeatherDtoMapper
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainModel {
        val weatherDto = weatherApi.fetchWeather(lat, lon)
        return weatherDtoMapper.mapToDomainModel(weatherDto)
    }
}