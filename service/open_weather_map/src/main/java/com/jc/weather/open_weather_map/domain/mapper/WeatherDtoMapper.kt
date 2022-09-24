package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.data.dto.WeatherDto
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
