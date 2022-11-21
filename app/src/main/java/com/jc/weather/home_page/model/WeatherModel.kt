package com.jc.weather.home_page.model

data class WeatherModel(
    var city: String,
    var iconUrl: String,
    var temperature: String,
    var weatherDescription: String,
    var dailyForecasts: List<DayForecast>
)

data class DayForecast(
    val id: Long?,
    var dayName: String,
    val morning: Float,
    val day: Float,
    val evening: Float,
    val night: Float,
    val min: Float,
    val max: Float
)
