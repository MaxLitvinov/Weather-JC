package com.jc.weather.pages.home.model

data class WeatherUiModel(
    var city: String = "",
    var temperature: String = "",
    var weatherDescription: String? = null,
    var weekForecast: List<DayForecast> = emptyList()
)

data class DayForecast(
    val dayName: String,
    val dayTemp: Float,
    val nightTemp: Float
)