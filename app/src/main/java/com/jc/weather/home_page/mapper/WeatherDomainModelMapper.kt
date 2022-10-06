package com.jc.weather.home_page.mapper

import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
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
