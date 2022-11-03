package com.jc.weather.open_weather_map.domain.model

sealed class WeatherDomainResult {

    data class Success(val model: WeatherDomainModel) : WeatherDomainResult()

    data class Failure(val error: String?) : WeatherDomainResult()
}

data class WeatherDomainModel(
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    val timezoneOffset: Int,
    val currentWeather: CurrentWeatherDomainModel,
    val dailyForecasts: List<DailyDomainModel>
)

data class CurrentWeatherDomainModel(
    val currentTime: Long,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val temperature: Float,
    val feelsLike: Float,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Float,
    val clouds: Int,
    val uvIndex: Float,
    val visibility: Int,
    val windDeg: Int,
    val windGust: Float,
    val windSpeed: Float,
    val lastHourRainVolume: Float?,
    val lastHourSnowVolume: Float?,
    val weatherDetails: WeatherDetailsDomainModel
)

data class WeatherDetailsDomainModel(
    val id: Int,
    val mainDescription: String,
    val detailedDescription: String,
    val iconUrl: String
)

data class HourlyDomainModel(
    val time: Long,
    val temperature: Float,
    val feelsLike: Float,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Float,
    val uvIndex: Float,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Float,
    val windDeg: Int,
    val windGust: Float,
    val weather: WeatherDetailsDomainModel,
    val pop: Float
)

data class DailyDomainModel(
    val time: Long,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val moonriseTime: Long,
    val moonsetTime: Long,
    val moonPhase: Float,
    val temperature: TemperatureDomainModel,
    val feelsLike: FeelsLikeDomainModel,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Float,
    val windSpeed: Float,
    val windDeg: Int,
    val windGust: Float,
    val clouds: Int,
    val uvIndex: Float,
    val pop: Float,
    val rain: Float?,
    val snow: Float?,
    val hourlyForecasts: List<HourlyDomainModel>? = null
)

data class TemperatureDomainModel(
    val morning: Float,
    val day: Float,
    val evening: Float,
    val night: Float,
    val min: Float,
    val max: Float
)

data class FeelsLikeDomainModel(
    val day: Float,
    val night: Float,
    val evening: Float,
    val morning: Float
)
