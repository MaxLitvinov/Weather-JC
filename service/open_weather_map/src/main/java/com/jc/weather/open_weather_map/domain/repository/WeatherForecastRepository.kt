package com.jc.weather.open_weather_map.domain.repository

import com.jc.weather.api_client.di.NetworkResult
import com.jc.weather.api_client.di.handleApi
import com.jc.weather.open_weather_map.domain.mapper.WeatherDtoMapper
import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import com.jc.weather.open_weather_map.domain.model.WeatherDomainResult
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val api: OpenWeatherMapApi,
    private val mapper: WeatherDtoMapper
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainResult =
        when (val networkResult: NetworkResult<WeatherDto, Nothing> = handleApi { api.fetchWeather(lat, lon) }) {
            is NetworkResult.Success -> {
                val weatherDto = networkResult.data
                WeatherDomainResult.Success(mapper.mapToDomainModel(weatherDto))
            }
            is NetworkResult.Error -> {
                val errorCode = networkResult.code
                val errorMessage = networkResult.message
                WeatherDomainResult.Failure("Error code: $errorCode, message: $errorMessage")
            }
            is NetworkResult.Exception -> {
                val errorMessage = networkResult.error
                WeatherDomainResult.Failure("Error message: $errorMessage")
            }
        }
}
