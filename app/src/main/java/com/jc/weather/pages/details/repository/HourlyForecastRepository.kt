package com.jc.weather.pages.details.repository

import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.pages.details.mapper.WeatherDomainModelMapper
import com.jc.weather.pages.details.model.CollapsedModel
import javax.inject.Inject

class HourlyForecastRepository @Inject constructor(
    private val weatherDataStoreRepository: WeatherDataStoreRepository,
    private val mapper: WeatherDomainModelMapper
) {

    suspend fun fetchForecast(): List<CollapsedModel> =
        weatherDataStoreRepository.getData()
            ?.hourlyForecasts
            ?.map(mapper::mapToCollapsedModel)
            ?: emptyList()
}
