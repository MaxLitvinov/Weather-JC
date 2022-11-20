package com.jc.weather.open_weather_map.domain.repository

import com.jc.weather.api_client.di.factory.network_response.NetworkResponse
import com.jc.weather.logger.Logger
import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import com.jc.weather.open_weather_map.domain.mapper.WeatherDtoMapper
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainResult
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val api: OpenWeatherMapApi,
    private val mapper: WeatherDtoMapper,
    private val logger: Logger
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainResult =
        when (val response: NetworkResponse<WeatherDto, Any> = api.fetchWeather(lat, lon)) {
            is NetworkResponse.Success -> {
                val model: WeatherDomainModel = mapper.mapToDomainModel(response.body)
                WeatherDomainResult.Success(model)
            }
            is NetworkResponse.ServerError -> {
                logger.report("Server error code: ${response.code}, message: ${response.error?.message}")
                WeatherDomainResult.Failure("Server error code: ${response.code}, message: ${response.error?.message}")
            }
            is NetworkResponse.NetworkError -> {
                logger.report("Network error body - ${response.body}, message - ${response.error.message}")
                WeatherDomainResult.Failure("Network error body - ${response.body}, message - ${response.error.message}")
            }
            is NetworkResponse.UnknownError -> {
                logger.report("Unknown error code - ${response.code}")
                WeatherDomainResult.Failure("Unknown error code - ${response.code}")
            }
        }
}
