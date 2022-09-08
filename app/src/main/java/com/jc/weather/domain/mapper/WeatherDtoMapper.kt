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
            lat = lat,
            lon = lon,
            timezone = timezone,
            timezone_offset = timezone_offset,
            current = currentDtoMapper.mapToDomainModel(current),
            daily = daily.map(dailyDtoMapper::mapToDomainModel),
        )
    }
}