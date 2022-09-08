package com.jc.weather.pages.home.model

data class WeatherModel(
    var city: String = "",
    var temperature: String = "",
    var weatherDescription: String? = null,
    var weekForecast: List<DayForecast> = emptyList()
)

data class DayForecast(
    val dayName: String,
    val morning: Double,
    val day: Double,
    val evening: Double,
    val night: Double,
    val min: Double,
    val max: Double
)