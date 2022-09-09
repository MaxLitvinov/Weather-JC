package com.jc.weather.pages.home.model

data class WeatherModel(
    var city: String,
    var iconUrl: String,
    var temperature: String,
    var weatherDescription: String,
    var dailyForecasts: List<DayForecast>
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