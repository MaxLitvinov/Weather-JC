package com.jc.weather.domain.model

data class WeatherDomainModel(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: CurrentDomainModel,
    val daily: List<DailyDomainModel>
)

data class CurrentDomainModel(
    val currentTime: Long,
    val sunrise: Long,
    val sunset: Long,
    val temperature: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val clouds: Int,
    val uvi: Double,
    val visibility: Int,
    val windDeg: Int,
    val windGust: Double,
    val windSpeed: Double,
    val rain: Any?,
    val snow: Any?,
    val weather: WeatherDetailsDomainModel
)

data class WeatherDetailsDomainModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class DailyDomainModel(
    val time: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temperature: TemperatureDomainModel,
    val feelsLike: FeelsLikeDomainModel,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val windGust: Double,
    val clouds: Int,
    val uvi: Double,
    val pop: Double,
    val rain: Any?,
    val snow: Any?
)

data class TemperatureDomainModel(
    val morning: Double,
    val day: Double,
    val evening: Double,
    val night: Double,
    val min: Double,
    val max: Double
)

data class FeelsLikeDomainModel(
    val day: Double,
    val night: Double,
    val evening: Double,
    val morning: Double
)