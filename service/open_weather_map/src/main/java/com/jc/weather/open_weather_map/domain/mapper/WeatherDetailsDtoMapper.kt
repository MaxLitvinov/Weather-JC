package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
import javax.inject.Inject

class WeatherDetailsDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: WeatherDetailsDto) = with(dto) {
        WeatherDetailsDomainModel(
            id = id,
            mainDescription = mainDescription,
            detailedDescription = description,
            iconUrl = getIconUrl(icon)
        )
    }

    private fun getIconUrl(iconName: String): String =
        "https://openweathermap.org/img/wn/$iconName@2x.png"
}
