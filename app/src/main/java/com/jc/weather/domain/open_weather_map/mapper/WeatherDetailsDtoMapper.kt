package com.jc.weather.domain.open_weather_map.mapper

import com.jc.weather.data.open_weather_map.WeatherDetailsDto
import com.jc.weather.domain.open_weather_map.model.WeatherDetailsDomainModel
import javax.inject.Inject

class WeatherDetailsDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: WeatherDetailsDto) = with(dto) {
        WeatherDetailsDomainModel(
            id = id,
            mainDescription = mainDescription,
            detailedDescription = description,
            icon = icon
        )
    }
}
