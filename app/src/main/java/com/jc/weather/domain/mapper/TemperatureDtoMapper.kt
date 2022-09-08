package com.jc.weather.domain.mapper

import com.jc.weather.data.TemperatureDto
import com.jc.weather.domain.model.TemperatureDomainModel
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