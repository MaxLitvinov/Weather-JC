package com.jc.weather.domain.mapper

import com.jc.weather.data.WeatherDetailsDto
import com.jc.weather.domain.model.WeatherDetailsDomainModel
import javax.inject.Inject

class WeatherDetailsDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: WeatherDetailsDto) = with(dto) {
        WeatherDetailsDomainModel(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }
}