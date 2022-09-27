package com.jc.weather.pages.home.model

data class WeatherModel(
    var city: String,
    var iconUrl: String,
    var temperature: String,
    var weatherDescription: String,
    var dailyForecasts: List<DayForecast>
)

data class DayForecast(
    var dayName: String,
    val morning: Float,
    val day: Float,
    val evening: Float,
    val night: Float,
    val min: Float,
    val max: Float
)
