package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import com.jc.weather.open_weather_map.data.dto.CurrentDto
import com.jc.weather.open_weather_map.data.dto.DailyDto
import com.jc.weather.open_weather_map.data.dto.FeelsLikeDto
import com.jc.weather.open_weather_map.data.dto.RainDto
import com.jc.weather.open_weather_map.data.dto.TemperatureDto
import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import com.jc.weather.open_weather_map.domain.mapper.WeatherDtoMapper
import com.jc.weather.open_weather_map.domain.model.CurrentWeatherDomainModel
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var api: OpenWeatherMapApi
    private lateinit var weatherDtoMapper: WeatherDtoMapper
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        api = mockk()
        weatherDtoMapper = mockk()
        repository = WeatherRepository(api, weatherDtoMapper)
    }

    @Test
    fun `Check fetch weather`() = runBlocking {
        val latitude = 51.5008
        val longitude = 31.2945

        val currentDto = CurrentDto(currentTime = 1663109294, sunriseTime = 1663126067, sunsetTime = 1663172008, temperature = 9.69, feelsLike = 8.63, pressure = 1006, humidity = 94, dewPoint = 8.77, uvIndex = 0.0, clouds = 100, visibility = 10000, windSpeed = 2.26, windDeg = 317, windGust = 5.95, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10n")), rain = RainDto(lastHourVolume = 0.12), snow = null)
        val today = DailyDto(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64, temperature = TemperatureDto(day = 15.07, min = 9.34, max = 17.26, night = 11.26, evening = 12.31, morning = 9.34), feelsLike = FeelsLikeDto(day = 14.43, night = 10.9, evening = 11.97, morning = 8.52), pressure = 1005, humidity = 69, dewPoint = 9.46, windSpeed = 6.0, windDeg = 254, windGust = 8.66, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 93, pop = 0.57, rain = 2.39, uvIndex = 2.34, snow = null)
        val tomorrow = DailyDto(time = 1663232400, sunriseTime = 1663212562, sunsetTime = 1663258269, moonriseTime = 1663265160, moonsetTime = 1663234260, moonPhase = 0.67, temperature = TemperatureDto(day = 18.2, min = 11.13, max = 20.8, night = 14.32, evening = 17.43, morning = 11.64), feelsLike = FeelsLikeDto(day = 17.96, night = 14.31, evening = 17.24, morning = 11.31), pressure = 1004, humidity = 72, dewPoint = 13.16, windSpeed = 5.45, windDeg = 210, windGust = 6.75, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.2, rain = 0.44, uvIndex = 3.32, snow = null)
        val day3 = DailyDto(time = 1663318800, sunriseTime = 1663299057, sunsetTime = 1663344530, moonriseTime = 1663353120, moonsetTime = 1663325160, moonPhase = 0.7, temperature = TemperatureDto(day = 15.62, min = 12.63, max = 20.5, night = 14.1, evening = 16.95, morning = 12.63), feelsLike = FeelsLikeDto(day = 15.54, night = 13.24, evening = 16.42, morning = 12.51), pressure = 1003, humidity = 88, dewPoint = 13.51, windSpeed = 5.91, windDeg = 266, windGust = 8.47, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.22, rain = 0.37, uvIndex = 3.22, snow = null)
        val day4 = DailyDto(time = 1663405200, sunriseTime = 1663385552, sunsetTime = 1663430790, moonriseTime = 1663441500, moonsetTime = 1663415700, moonPhase = 0.73, temperature = TemperatureDto(day = 16.87, min = 11.16, max = 16.87, night = 12.45, evening = 13.06, morning = 11.88), feelsLike = FeelsLikeDto(day = 16.07, night = 11.89, evening = 12.51, morning = 10.95), pressure = 1006, humidity = 56, dewPoint = 8.17, windSpeed = 4.34, windDeg = 215, windGust = 4.47, weather = listOf(WeatherDetailsDto(id = 804, mainDescription = "Clouds", description = "overcast clouds", icon = "04d")), clouds = 100, pop = 0.17, uvIndex = 2.53, rain = null, snow = null)
        val day5 = DailyDto(time = 1663491600, sunriseTime = 1663472047, sunsetTime = 1663517051, moonriseTime = 1663530540, moonsetTime = 1663505760, moonPhase = 0.75, temperature = TemperatureDto(day = 11.49, min = 9.91, max = 18.69, night = 9.91, evening = 18.69, morning = 11.99), feelsLike = FeelsLikeDto(day = 11.12, night = 6.42, evening = 18.91, morning = 11.23), pressure = 999, humidity = 93, dewPoint = 10.26, windSpeed = 10.2, windDeg = 163, windGust = 17.1, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 100, pop = 1.0, rain = 10.49, uvIndex = 0.24, snow = null)
        val day6 = DailyDto(time = 1663578000, sunriseTime = 1663558542, sunsetTime = 1663603311, moonriseTime = 1663620300, moonsetTime = 1663595100, moonPhase = 0.79, temperature = TemperatureDto(day = 13.92, min = 8.35, max = 15.78, night = 9.08, evening = 13.64, morning = 9.3), feelsLike = FeelsLikeDto(day = 12.88, night = 6.88, evening = 12.65, morning = 5.28), pressure = 1007, humidity = 58, dewPoint = 5.73, windSpeed = 10.52, windDeg = 249, windGust = 17.94, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 5, pop = 0.23, rain = 0.19, uvIndex = 1.0, snow = null)
        val day7 = DailyDto(time = 1663664400, sunriseTime = 1663645037, sunsetTime = 1663689572, moonriseTime = 0, moonsetTime = 1663683840, moonPhase = 0.82, temperature = TemperatureDto(day = 11.98, min = 7.19, max = 12.35, night = 11.08, evening = 12.35, morning = 7.19), feelsLike = FeelsLikeDto(day = 11.24, night = 10.46, evening = 11.73, morning = 4.28), pressure = 1014, humidity = 77, dewPoint = 8.06, windSpeed = 6.64, windDeg = 257, windGust = 11.41, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.27, rain = 0.12, uvIndex = 1.0, snow = null)
        val day8 = DailyDto(time = 1663750800, sunriseTime = 1663731533, sunsetTime = 1663775832, moonriseTime = 1663710600, moonsetTime = 1663771920, moonPhase = 0.85, temperature = TemperatureDto(day = 14.76, min = 8.89, max = 16.06, night = 12.74, evening = 14.19, morning = 8.89), feelsLike = FeelsLikeDto(day = 13.78, night = 11.66, evening = 13.21, morning = 7.93), pressure = 1021, humidity = 57, dewPoint = 6.44, windSpeed = 3.93, windDeg = 35, windGust = 8.24, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 96, pop = 0.2, rain = 0.13, uvIndex = 1.0, snow = null)
        val dailyForecasts = listOf(today, tomorrow, day3, day4, day5, day6, day7, day8)
        val weatherDto = WeatherDto(latitude = latitude, longitude = longitude, timezone = "Europe/Kiev", timezoneOffset = 10800, current = currentDto, dailyForecasts = dailyForecasts)

        val todayDomain = DailyDomainModel(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64, temperature = TemperatureDomainModel(day = 15.07, min = 9.34, max = 17.26, night = 11.26, evening = 12.31, morning = 9.34), feelsLike = FeelsLikeDomainModel(day = 14.43, night = 10.9, evening = 11.97, morning = 8.52), pressure = 1005, humidity = 69, dewPoint = 9.46, windSpeed = 6.0, windDeg = 254, windGust = 8.66, clouds = 93, pop = 0.57, rain = 2.39, uvIndex = 2.34, snow = null)
        val tomorrowDomain = DailyDomainModel(time = 1663232400, sunriseTime = 1663212562, sunsetTime = 1663258269, moonriseTime = 1663265160, moonsetTime = 1663234260, moonPhase = 0.67, temperature = TemperatureDomainModel(day = 18.2, min = 11.13, max = 20.8, night = 14.32, evening = 17.43, morning = 11.64), feelsLike = FeelsLikeDomainModel(day = 17.96, night = 14.31, evening = 17.24, morning = 11.31), pressure = 1004, humidity = 72, dewPoint = 13.16, windSpeed = 5.45, windDeg = 210, windGust = 6.75, clouds = 100, pop = 0.2, rain = 0.44, uvIndex = 3.32, snow = null)
        val day3Domain = DailyDomainModel(time = 1663318800, sunriseTime = 1663299057, sunsetTime = 1663344530, moonriseTime = 1663353120, moonsetTime = 1663325160, moonPhase = 0.7, temperature = TemperatureDomainModel(day = 15.62, min = 12.63, max = 20.5, night = 14.1, evening = 16.95, morning = 12.63), feelsLike = FeelsLikeDomainModel(day = 15.54, night = 13.24, evening = 16.42, morning = 12.51), pressure = 1003, humidity = 88, dewPoint = 13.51, windSpeed = 5.91, windDeg = 266, windGust = 8.47, clouds = 100, pop = 0.22, rain = 0.37, uvIndex = 3.22, snow = null)
        val day4Domain = DailyDomainModel(time = 1663405200, sunriseTime = 1663385552, sunsetTime = 1663430790, moonriseTime = 1663441500, moonsetTime = 1663415700, moonPhase = 0.73, temperature = TemperatureDomainModel(day = 16.87, min = 11.16, max = 16.87, night = 12.45, evening = 13.06, morning = 11.88), feelsLike = FeelsLikeDomainModel(day = 16.07, night = 11.89, evening = 12.51, morning = 10.95), pressure = 1006, humidity = 56, dewPoint = 8.17, windSpeed = 4.34, windDeg = 215, windGust = 4.47, clouds = 100, pop = 0.17, uvIndex = 2.53, rain = null, snow = null)
        val day5Domain = DailyDomainModel(time = 1663491600, sunriseTime = 1663472047, sunsetTime = 1663517051, moonriseTime = 1663530540, moonsetTime = 1663505760, moonPhase = 0.75, temperature = TemperatureDomainModel(day = 11.49, min = 9.91, max = 18.69, night = 9.91, evening = 18.69, morning = 11.99), feelsLike = FeelsLikeDomainModel(day = 11.12, night = 6.42, evening = 18.91, morning = 11.23), pressure = 999, humidity = 93, dewPoint = 10.26, windSpeed = 10.2, windDeg = 163, windGust = 17.1, clouds = 100, pop = 1.0, rain = 10.49, uvIndex = 0.24, snow = null)
        val day6Domain = DailyDomainModel(time = 1663578000, sunriseTime = 1663558542, sunsetTime = 1663603311, moonriseTime = 1663620300, moonsetTime = 1663595100, moonPhase = 0.79, temperature = TemperatureDomainModel(day = 13.92, min = 8.35, max = 15.78, night = 9.08, evening = 13.64, morning = 9.3), feelsLike = FeelsLikeDomainModel(day = 12.88, night = 6.88, evening = 12.65, morning = 5.28), pressure = 1007, humidity = 58, dewPoint = 5.73, windSpeed = 10.52, windDeg = 249, windGust = 17.94, clouds = 5, pop = 0.23, rain = 0.19, uvIndex = 1.0, snow = null)
        val day7Domain = DailyDomainModel(time = 1663664400, sunriseTime = 1663645037, sunsetTime = 1663689572, moonriseTime = 0, moonsetTime = 1663683840, moonPhase = 0.82, temperature = TemperatureDomainModel(day = 11.98, min = 7.19, max = 12.35, night = 11.08, evening = 12.35, morning = 7.19), feelsLike = FeelsLikeDomainModel(day = 11.24, night = 10.46, evening = 11.73, morning = 4.28), pressure = 1014, humidity = 77, dewPoint = 8.06, windSpeed = 6.64, windDeg = 257, windGust = 11.41, clouds = 100, pop = 0.27, rain = 0.12, uvIndex = 1.0, snow = null)
        val day8Domain = DailyDomainModel(time = 1663750800, sunriseTime = 1663731533, sunsetTime = 1663775832, moonriseTime = 1663710600, moonsetTime = 1663771920, moonPhase = 0.85, temperature = TemperatureDomainModel(day = 14.76, min = 8.89, max = 16.06, night = 12.74, evening = 14.19, morning = 8.89), feelsLike = FeelsLikeDomainModel(day = 13.78, night = 11.66, evening = 13.21, morning = 7.93), pressure = 1021, humidity = 57, dewPoint = 6.44, windSpeed = 3.93, windDeg = 35, windGust = 8.24, clouds = 96, pop = 0.2, rain = 0.13, uvIndex = 1.0, snow = null)
        val expectedWeatherDomainModel = WeatherDomainModel(latitude = latitude, longitude = longitude, timezone = "Europe/Kiev", timezoneOffset = 10800, currentWeather = CurrentWeatherDomainModel(currentTime = 1663109294, sunriseTime = 1663126067, sunsetTime = 1663172008, temperature = 9.69, feelsLike = 8.63, pressure = 1006, humidity = 94, dewPoint = 8.77, uvIndex = 0.0, clouds = 100, visibility = 10000, windSpeed = 2.26, windDeg = 317, windGust = 5.95, weatherDetails = WeatherDetailsDomainModel(id = 500, mainDescription = "Rain", detailedDescription = "light rain", iconUrl = "https://openweathermap.org/img/wn/10n@2x.png"), lastHourRainVolume = 0.12, lastHourSnowVolume = null), dailyForecasts = listOf(todayDomain, tomorrowDomain, day3Domain, day4Domain, day5Domain, day6Domain, day7Domain, day8Domain))

        coEvery { api.fetchWeather("$latitude", "$longitude") } returns weatherDto
        every { weatherDtoMapper.mapToDomainModel(weatherDto) } returns expectedWeatherDomainModel

        val actualWeatherDomainModel = repository.fetchWeather("$latitude", "$longitude")

        Assert.assertEquals(expectedWeatherDomainModel, actualWeatherDomainModel)
    }
}
