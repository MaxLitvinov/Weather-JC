package com.jc.weather.open_weather_map.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param latitude Geographical coordinates of the location (latitude)
 * @param longitude Geographical coordinates of the location (longitude)
 * @param timezone Timezone name for the requested location
 * @param timezoneOffset Shift in seconds from UTC
 * @param current Current weather data API response
 * @param hourlyForecasts Hourly forecast weather data API response
 * @param dailyForecasts Daily forecasts weather data API response
 *
 * @see <a href="https://openweathermap.org/api/one-call-3">OpenWeather One Call API 3.0.</a>
 */
data class WeatherDto(
    @SerializedName("lat")
    val latitude: Float,
    @SerializedName("lon")
    val longitude: Float,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    @SerializedName("current")
    val current: CurrentDto,
    @SerializedName("hourly")
    val hourlyForecasts: List<HourlyDto>? = null,
    @SerializedName("daily")
    val dailyForecasts: List<DailyDto>
)

/**
 * This is the current weather data.
 *
 * @param currentTime Current time, Unix, UTC
 * @param sunriseTime Sunrise time, Unix, UTC
 * @param sunsetTime Sunset time, Unix, UTC
 * @param temperature Temperature. Units - default: kelvin, metric: Celsius, imperial: Fahrenheit. How to change units used
 * @param feelsLike Temperature. This temperature parameter accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param pressure Atmospheric pressure on the sea level, hPa
 * @param humidity Humidity, %
 * @param dewPoint Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param clouds Cloudiness, %
 * @param uvIndex Current UV index
 * @param visibility Average visibility, metres. The maximum value of the visibility is 10km
 * @param windSpeed Wind speed. Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour. How to change units used
 * @param windGust (where available) Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour. How to change units used
 * @param windDeg Wind direction, degrees (meteorological)
 * @param rain rain.1h (where available) Rain volume for last hour, mm
 * @param snow snow.1h (where available) Snow volume for last hour, mm
 * @param weather weather details
 */
data class CurrentDto(
    @SerializedName("dt")
    val currentTime: Long,
    @SerializedName("sunrise")
    val sunriseTime: Long,
    @SerializedName("sunset")
    val sunsetTime: Long,
    @SerializedName("temp")
    val temperature: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Float,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("uvi")
    val uvIndex: Float,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("rain")
    val rain: RainDto?,
    @SerializedName("snow")
    val snow: SnowDto?,
    @SerializedName("weather")
    val weather: List<WeatherDetailsDto>
)

/**
 * @param id Weather condition id
 * @param mainDescription Group of weather parameters (Rain, Snow, Extreme etc.)
 * @param description Weather condition within the group
 * @param icon Weather icon id
 */
data class WeatherDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val mainDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

/**
 * @param time Time of the forecasted data, Unix, UTC
 * @param temperature Temperature. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param feelsLike Temperature. This accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param pressure Atmospheric pressure on the sea level, hPa
 * @param humidity Humidity, %
 * @param dewPoint Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param uvIndex UV index
 * @param clouds Cloudiness, %
 * @param visibility Average visibility, metres. The maximum value of the visibility is 10km
 * @param windSpeed Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.How to change units used
 * @param windDeg Wind direction, degrees (meteorological)
 * @param windGust (where available) Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
 * @param weather Weather details
 * @param pop Probability of precipitation. The values of the parameter vary between 0 and 1, where 0 is equal to 0%, 1 is equal to 100%
 */
data class HourlyDto(
    @SerializedName("dt")
    val time: Long,
    @SerializedName("temp")
    val temperature: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Float,
    @SerializedName("uvi")
    val uvIndex: Float,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Float,
    @SerializedName("weather")
    val weather: List<WeatherDetailsDto>,
    @SerializedName("pop")
    val pop: Float
)

/**
 * Daily forecast weather data
 *
 * @param time Time of the forecasted data, Unix, UTC
 * @param sunriseTime Sunrise time, Unix, UTC
 * @param sunsetTime Sunset time, Unix, UTC
 * @param moonriseTime The time of when the moon rises for this day, Unix, UTC
 * @param moonsetTime The time of when the moon sets for this day, Unix, UTC
 * @param moonPhase Moon phase. 0 and 1 are 'new moon', 0.25 is 'first quarter moon', 0.5 is 'full moon' and 0.75 is 'last quarter moon'. The periods in between are called 'waxing crescent', 'waxing gibous', 'waning gibous', and 'waning crescent', respectively.
 * @param temperature Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
 * @param feelsLike This accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param pressure Atmospheric pressure on the sea level, hPa
 * @param humidity Humidity, %
 * @param dewPoint Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 * @param windSpeed Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
 * @param windDeg Wind direction, degrees (meteorological)
 * @param windGust (where available) Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour. How to change units used
 * @param clouds Cloudiness, %
 * @param uvIndex The maximum value of UV index for the day
 * @param pop Probability of precipitation. The values of the parameter vary between 0 and 1, where 0 is equal to 0%, 1 is equal to 100%
 * @param rain (where available) Precipitation volume, mm
 * @param snow (where available) Snow volume, mm
 * @param weather Weather details
 */
data class DailyDto(
    @SerializedName("dt")
    val time: Long,
    @SerializedName("sunrise")
    val sunriseTime: Long,
    @SerializedName("sunset")
    val sunsetTime: Long,
    @SerializedName("moonrise")
    val moonriseTime: Long,
    @SerializedName("moonset")
    val moonsetTime: Long,
    @SerializedName("moon_phase")
    val moonPhase: Float,
    @SerializedName("temp")
    val temperature: TemperatureDto,
    @SerializedName("feels_like")
    val feelsLike: FeelsLikeDto,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Float,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("uvi")
    val uvIndex: Float,
    @SerializedName("pop")
    val pop: Float,
    @SerializedName("rain")
    val rain: Float?,
    @SerializedName("snow")
    val snow: Float?,
    @SerializedName("weather")
    val weather: List<WeatherDetailsDto>
)

/**
 * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
 *
 * @param morning Morning temperature
 * @param day Day temperature
 * @param evening Evening temperature
 * @param night Night temperature
 * @param min Min daily temperature
 * @param max Max daily temperature
 *
 * @see <a href="https://openweathermap.org/api/one-call-3#data">How to change units used</a>
 */
data class TemperatureDto(
    @SerializedName("morn")
    val morning: Float,
    @SerializedName("day")
    val day: Float,
    @SerializedName("eve")
    val evening: Float,
    @SerializedName("night")
    val night: Float,
    @SerializedName("min")
    val min: Float,
    @SerializedName("max")
    val max: Float
)

/**
 * This accounts for the human perception of weather.
 *
 * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
 *
 * @param morning Morning temperature
 * @param day Day temperature
 * @param evening Evening temperature
 * @param night Night temperature
 *
 * @see <a href="https://openweathermap.org/api/one-call-3#data">How to change units used</a>
 */
data class FeelsLikeDto(
    @SerializedName("day")
    val day: Float,
    @SerializedName("night")
    val night: Float,
    @SerializedName("eve")
    val evening: Float,
    @SerializedName("morn")
    val morning: Float
)

/**
 * Rain volume, mm
 *
 * @param lastHourVolume (where available) Rain volume for last hour, mm
 */
data class RainDto(
    @SerializedName("1h")
    val lastHourVolume: Float
)

/**
 * Snow volume, mm
 *
 * @param lastHourVolume (where available) Snow volume for last hour, mm
 */
data class SnowDto(
    @SerializedName("1h")
    val lastHourVolume: Float
)
