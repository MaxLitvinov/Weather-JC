package com.jc.weather.pages.home.mapper

import com.jc.weather.domain.model.WeatherDomainModel
import com.jc.weather.pages.home.model.WeatherModel
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    private val dailyDomainModelMapper: DailyDomainModelMapper
) {

    fun mapToUiModel(domainModel: WeatherDomainModel) = with(domainModel) {
        WeatherModel(
            city = "Unknown city",
            temperature = current.temperature.toString(),
            weatherDescription = current.weather.description,
            weekForecast = daily.map(dailyDomainModelMapper::mapToUiModel)
        )
    }
}