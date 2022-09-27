package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.data.dto.HourlyDto
import com.jc.weather.open_weather_map.domain.model.HourlyDomainModel
import javax.inject.Inject

class HourlyDtoMapper @Inject constructor(
    private val weatherMapper: WeatherDetailsDtoMapper
) {

    fun mapToDomainModel(dto: HourlyDto) = with(dto) {
        HourlyDomainModel(
            time = time,
            temperature = temperature,
            feelsLike = feelsLike,
            pressure = pressure,
            humidity = humidity,
            dewPoint = dewPoint,
            uvIndex = uvIndex,
            clouds = clouds,
            visibility = visibility,
            windSpeed = windSpeed,
            windDeg = windDeg,
            windGust = windGust,
            weather = weatherMapper.mapToDomainModel(weather.first()),
            pop = pop
        )
    }
}
