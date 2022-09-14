package com.jc.weather.domain.open_weather_map.mapper

import com.jc.weather.data.open_weather_map.TemperatureDto
import com.jc.weather.domain.open_weather_map.model.TemperatureDomainModel
import javax.inject.Inject

class TemperatureDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: TemperatureDto) = with(dto) {
        TemperatureDomainModel(
            morning = morning,
            day = day,
            evening = evening,
            night = night,
            min = min,
            max = max
        )
    }
}
