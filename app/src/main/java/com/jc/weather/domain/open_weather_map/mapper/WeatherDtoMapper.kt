package com.jc.weather.domain.open_weather_map.mapper

import com.jc.weather.data.open_weather_map.WeatherDto
import com.jc.weather.domain.open_weather_map.model.WeatherDomainModel
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
