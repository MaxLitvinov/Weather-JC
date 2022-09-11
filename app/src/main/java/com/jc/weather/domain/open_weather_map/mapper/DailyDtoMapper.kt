package com.jc.weather.domain.open_weather_map.mapper

import com.jc.weather.data.open_weather_map.DailyDto
import com.jc.weather.domain.open_weather_map.model.DailyDomainModel
import javax.inject.Inject

class DailyDtoMapper @Inject constructor(
    private val temperatureDtoMapper: TemperatureDtoMapper,
    private val feelsLikeDtoMapper: FeelsLikeDtoMapper
) {

    fun mapToDomainModel(dto: DailyDto) = with(dto) {
        DailyDomainModel(
            time = time,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            moonriseTime = moonriseTime,
            moonsetTime = moonsetTime,
            moonPhase = moonPhase,
            temperature = temperatureDtoMapper.mapToDomainModel(temperature),
            feelsLike = feelsLikeDtoMapper.mapToDomainModel(feelsLike),
            pressure = pressure,
            humidity = humidity,
            dewPoint = dewPoint,
            windSpeed = windSpeed,
            windDeg = windDeg,
            windGust = windGust,
            clouds = clouds,
            uvIndex = uvIndex,
            pop = pop,
            rain = rain,
            snow = snow
        )
    }
}
