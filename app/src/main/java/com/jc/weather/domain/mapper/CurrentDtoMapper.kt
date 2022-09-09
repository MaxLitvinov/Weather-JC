package com.jc.weather.domain.mapper

import com.jc.weather.data.CurrentDto
import com.jc.weather.domain.model.CurrentWeatherDomainModel
import javax.inject.Inject

class CurrentDtoMapper @Inject constructor(
    private val weatherDetailsDtoMapper: WeatherDetailsDtoMapper
) {

    fun mapToDomainModel(dto: CurrentDto) = with(dto) {
        CurrentWeatherDomainModel(
            currentTime = currentTime,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            temperature = temperature,
            feelsLike = feelsLike,
            pressure = pressure,
            humidity = humidity,
            dewPoint = dewPoint,
            clouds = clouds,
            uvIndex = uvIndex,
            visibility = visibility,
            windDeg = windDeg,
            windGust = windGust,
            windSpeed = windSpeed,
            rain = rain,
            snow = snow,
            weatherDetails = weatherDetailsDtoMapper.mapToDomainModel(weather.first())
        )
    }
}