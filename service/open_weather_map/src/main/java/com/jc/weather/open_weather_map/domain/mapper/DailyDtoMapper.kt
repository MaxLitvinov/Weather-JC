package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.data.dto.DailyDto
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
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
