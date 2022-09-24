package com.jc.weather.pages.home.mapper

import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.pages.home.model.WeatherModel
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    private val dailyDomainModelMapper: DailyDomainModelMapper
) {

    fun mapToUiModel(domainModel: WeatherDomainModel) = with(domainModel) {
        WeatherModel(
            city = "",
            iconUrl = currentWeather.weatherDetails.iconUrl,
            temperature = currentWeather.temperature.toString(),
            weatherDescription = currentWeather.weatherDetails.detailedDescription
                .replaceFirstChar { it.titlecase() },
            dailyForecasts = dailyForecasts.map(dailyDomainModelMapper::mapToUiModel)
        )
    }
}
