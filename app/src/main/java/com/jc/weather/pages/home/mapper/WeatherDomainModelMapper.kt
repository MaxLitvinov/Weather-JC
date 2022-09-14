package com.jc.weather.pages.home.mapper

import com.jc.weather.domain.open_weather_map.model.WeatherDomainModel
import com.jc.weather.pages.home.model.WeatherModel
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    private val dailyDomainModelMapper: DailyDomainModelMapper
) {

    fun mapToUiModel(domainModel: WeatherDomainModel) = with(domainModel) {
        WeatherModel(
            city = "",
            iconUrl = getIconUrl(currentWeather.weatherDetails.icon),
            temperature = currentWeather.temperature.toString(),
            weatherDescription = currentWeather.weatherDetails.detailedDescription
                .replaceFirstChar { it.titlecase() },
            dailyForecasts = dailyForecasts.map(dailyDomainModelMapper::mapToUiModel)
        )
    }

    private fun getIconUrl(iconName: String): String =
        "https://openweathermap.org/img/wn/$iconName@2x.png"
}
