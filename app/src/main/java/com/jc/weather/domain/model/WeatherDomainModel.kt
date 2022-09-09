package com.jc.weather.domain.model

data class WeatherDomainModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneOffset: Int,
    val currentWeather: CurrentWeatherDomainModel,
    val dailyForecasts: List<DailyDomainModel>
)

data class CurrentWeatherDomainModel(
    val currentTime: Long,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val temperature: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val clouds: Int,
    val uvIndex: Double,
    val visibility: Int,
    val windDeg: Int,
    val windGust: Double,
    val windSpeed: Double,
    val rain: Any?,
    val snow: Any?,
    val weatherDetails: WeatherDetailsDomainModel
)

data class WeatherDetailsDomainModel(
    val id: Int,
    val mainDescription: String,
    val detailedDescription: String,
    val icon: String
)

data class DailyDomainModel(
    val time: Long,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val moonriseTime: Long,
    val moonsetTime: Long,
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
    val uvIndex: Double,
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