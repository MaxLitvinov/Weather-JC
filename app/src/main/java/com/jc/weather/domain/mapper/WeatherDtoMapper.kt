package com.jc.weather.domain.mapper

import com.jc.weather.data.WeatherDto
import com.jc.weather.domain.model.WeatherDomainModel
import javax.inject.Inject

class WeatherDtoMapper @Inject constructor(
    private val currentDtoMapper: CurrentDtoMapper,
    private val dailyDtoMapper: DailyDtoMapper
) {

    fun mapToDomainModel(dto: WeatherDto) = with(dto) {
        WeatherDomainModel(
            latitude = latitude,
            longitude = longitude,
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            currentWeather = currentDtoMapper.mapToDomainModel(current),
            dailyForecasts = dailyForecasts.map(dailyDtoMapper::mapToDomainModel),
        )
    }
}