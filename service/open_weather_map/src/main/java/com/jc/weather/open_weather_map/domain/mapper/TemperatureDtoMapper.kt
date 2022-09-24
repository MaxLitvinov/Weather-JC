package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import com.jc.weather.open_weather_map.data.dto.TemperatureDto
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
