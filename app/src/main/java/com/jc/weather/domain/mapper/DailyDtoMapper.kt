package com.jc.weather.domain.mapper

import com.jc.weather.data.DailyDto
import com.jc.weather.domain.model.DailyDomainModel
import javax.inject.Inject

class DailyDtoMapper @Inject constructor(
    private val temperatureDtoMapper: TemperatureDtoMapper,
    private val feelsLikeDtoMapper: FeelsLikeDtoMapper
) {

    fun mapToDomainModel(dto: DailyDto) = with(dto) {
        DailyDomainModel(
            time = time,
            sunrise = sunrise,
            sunset = sunset,
            moonrise = moonrise,
            moonset = moonset,
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
            uvi = uvi,
            pop = pop,
            rain = rain,
            snow = snow
        )
    }
}