package com.jc.weather.domain.mapper

import com.jc.weather.data.CurrentDto
import com.jc.weather.domain.model.CurrentDomainModel
import javax.inject.Inject

class CurrentDtoMapper @Inject constructor(
    private val weatherDetailsDtoMapper: WeatherDetailsDtoMapper
) {

    fun mapToDomainModel(dto: CurrentDto) = with(dto) {
        CurrentDomainModel(
            currentTime = currentTime,
            sunrise = sunrise,
            sunset = sunset,
            temperature = temperature,
            feelsLike = feelsLike,
            pressure = pressure,
            humidity = humidity,
            dewPoint = dewPoint,
            clouds = clouds,
            uvi = uvi,
            visibility = visibility,
            windDeg = windDeg,
            windGust = windGust,
            windSpeed = windSpeed,
            rain = rain,
            snow = snow,
            weather = weatherDetailsDtoMapper.mapToDomainModel(weather.first())
        )
    }
}