package com.jc.weather.hourly_forecast.repository

import com.jc.weather.hourly_forecast.mapper.WeatherDomainModelMapper
import com.jc.weather.hourly_forecast.model.CollapsedModel
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import javax.inject.Inject

class HourlyForecastRepository @Inject constructor(
    private val weatherDataStoreRepository: WeatherDataStoreRepository,
    private val mapper: WeatherDomainModelMapper
) {

    suspend fun fetchForecast(dayId: String?): List<CollapsedModel> {
        if (dayId.isNullOrBlank()) {
            return emptyList()
        }
        val dayTime = dayId.toLong()
        return weatherDataStoreRepository.getData()
            ?.dailyForecasts
            ?.find { it.time == dayTime }
            ?.hourlyForecasts
            ?.map(mapper::mapToCollapsedModel)
            ?: emptyList()
    }
}
