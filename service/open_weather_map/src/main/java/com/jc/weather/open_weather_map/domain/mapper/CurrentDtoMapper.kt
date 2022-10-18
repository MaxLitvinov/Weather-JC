package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.data.dto.CurrentDto
import com.jc.weather.open_weather_map.domain.model.CurrentWeatherDomainModel
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
            lastHourRainVolume = rain?.lastHourVolume,
            lastHourSnowVolume = snow?.lastHourVolume,
            weatherDetails = weatherDetailsDtoMapper.mapToDomainModel(weather.first())
        )
    }
}
