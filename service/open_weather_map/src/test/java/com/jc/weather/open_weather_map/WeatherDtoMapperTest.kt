package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.CurrentDto
import com.jc.weather.open_weather_map.data.dto.DailyDto
import com.jc.weather.open_weather_map.data.dto.FeelsLikeDto
import com.jc.weather.open_weather_map.data.dto.RainDto
import com.jc.weather.open_weather_map.data.dto.TemperatureDto
import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import com.jc.weather.open_weather_map.domain.mapper.CurrentDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.DailyDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.HourlyDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.WeatherDtoMapper
import com.jc.weather.open_weather_map.domain.model.CurrentWeatherDomainModel
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherDtoMapperTest {

    private lateinit var currentDtoMapper: CurrentDtoMapper
    private lateinit var hourlyDtoMapper: HourlyDtoMapper
    private lateinit var dailyDtoMapper: DailyDtoMapper
    private lateinit var weatherDtoMapper: WeatherDtoMapper

    @Before
    fun setup() {
        currentDtoMapper = mockk()
        hourlyDtoMapper = mockk()
        dailyDtoMapper = mockk()
        weatherDtoMapper = WeatherDtoMapper(currentDtoMapper, hourlyDtoMapper, dailyDtoMapper)
    }

    @Test
    fun `Check WeatherDto mapping`() {
        val latitude = 51.5008F
        val longitude = 31.2945F

        val currentDto = CurrentDto(currentTime = 1663109294, sunriseTime = 1663126067, sunsetTime = 1663172008, temperature = 9.69F, feelsLike = 8.63F, pressure = 1006, humidity = 94, dewPoint = 8.77F, uvIndex = 0.0F, clouds = 100, visibility = 10000, windSpeed = 2.26F, windDeg = 317, windGust = 5.95F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10n")), rain = RainDto(lastHourVolume = 0.12F), snow = null)
        val today = DailyDto(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64F, temperature = TemperatureDto(day = 15.07F, min = 9.34F, max = 17.26F, night = 11.26F, evening = 12.31F, morning = 9.34F), feelsLike = FeelsLikeDto(day = 14.43F, night = 10.9F, evening = 11.97F, morning = 8.52F), pressure = 1005, humidity = 69, dewPoint = 9.46f, windSpeed = 6.0F, windDeg = 254, windGust = 8.66F, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 93, pop = 0.57F, rain = 2.39F, uvIndex = 2.34F, snow = null)
        val tomorrow = DailyDto(time = 1663232400, sunriseTime = 1663212562, sunsetTime = 1663258269, moonriseTime = 1663265160, moonsetTime = 1663234260, moonPhase = 0.67F, temperature = TemperatureDto(day = 18.2F, min = 11.13F, max = 20.8F, night = 14.32F, evening = 17.43F, morning = 11.64F), feelsLike = FeelsLikeDto(day = 17.96F, night = 14.31F, evening = 17.24F, morning = 11.31F), pressure = 1004, humidity = 72, dewPoint = 13.16f, windSpeed = 5.45F, windDeg = 210, windGust = 6.75F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.2F, rain = 0.44F, uvIndex = 3.32F, snow = null)
        val day3 = DailyDto(time = 1663318800, sunriseTime = 1663299057, sunsetTime = 1663344530, moonriseTime = 1663353120, moonsetTime = 1663325160, moonPhase = 0.7F, temperature = TemperatureDto(day = 15.62F, min = 12.63F, max = 20.5F, night = 14.1F, evening = 16.95F, morning = 12.63F), feelsLike = FeelsLikeDto(day = 15.54F, night = 13.24F, evening = 16.42F, morning = 12.51F), pressure = 1003, humidity = 88, dewPoint = 13.51f, windSpeed = 5.91F, windDeg = 266, windGust = 8.47F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.22F, rain = 0.37F, uvIndex = 3.22F, snow = null)
        val day4 = DailyDto(time = 1663405200, sunriseTime = 1663385552, sunsetTime = 1663430790, moonriseTime = 1663441500, moonsetTime = 1663415700, moonPhase = 0.73F, temperature = TemperatureDto(day = 16.87F, min = 11.16F, max = 16.87F, night = 12.45F, evening = 13.06F, morning = 11.88F), feelsLike = FeelsLikeDto(day = 16.07F, night = 11.89F, evening = 12.51F, morning = 10.95F), pressure = 1006, humidity = 56, dewPoint = 8.17f, windSpeed = 4.34F, windDeg = 215, windGust = 4.47F, weather = listOf(WeatherDetailsDto(id = 804, mainDescription = "Clouds", description = "overcast clouds", icon = "04d")), clouds = 100, pop = 0.17F, uvIndex = 2.53F, rain = null, snow = null)
        val day5 = DailyDto(time = 1663491600, sunriseTime = 1663472047, sunsetTime = 1663517051, moonriseTime = 1663530540, moonsetTime = 1663505760, moonPhase = 0.75F, temperature = TemperatureDto(day = 11.49F, min = 9.91F, max = 18.69F, night = 9.91F, evening = 18.69F, morning = 11.99F), feelsLike = FeelsLikeDto(day = 11.12F, night = 6.42F, evening = 18.91F, morning = 11.23F), pressure = 999, humidity = 93, dewPoint = 10.26f, windSpeed = 10.2F, windDeg = 163, windGust = 17.1F, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 100, pop = 1.0F, rain = 10.49F, uvIndex = 0.24F, snow = null)
        val day6 = DailyDto(time = 1663578000, sunriseTime = 1663558542, sunsetTime = 1663603311, moonriseTime = 1663620300, moonsetTime = 1663595100, moonPhase = 0.79F, temperature = TemperatureDto(day = 13.92F, min = 8.35F, max = 15.78F, night = 9.08F, evening = 13.64F, morning = 9.3F), feelsLike = FeelsLikeDto(day = 12.88F, night = 6.88F, evening = 12.65F, morning = 5.28F), pressure = 1007, humidity = 58, dewPoint = 5.73f, windSpeed = 10.52F, windDeg = 249, windGust = 17.94F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 5, pop = 0.23F, rain = 0.19F, uvIndex = 1.0F, snow = null)
        val day7 = DailyDto(time = 1663664400, sunriseTime = 1663645037, sunsetTime = 1663689572, moonriseTime = 0, moonsetTime = 1663683840, moonPhase = 0.82F, temperature = TemperatureDto(day = 11.98F, min = 7.19F, max = 12.35F, night = 11.08F, evening = 12.35F, morning = 7.19F), feelsLike = FeelsLikeDto(day = 11.24F, night = 10.46F, evening = 11.73F, morning = 4.28F), pressure = 1014, humidity = 77, dewPoint = 8.06f, windSpeed = 6.64F, windDeg = 257, windGust = 11.41F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 100, pop = 0.27F, rain = 0.12F, uvIndex = 1.0F, snow = null)
        val day8 = DailyDto(time = 1663750800, sunriseTime = 1663731533, sunsetTime = 1663775832, moonriseTime = 1663710600, moonsetTime = 1663771920, moonPhase = 0.85F, temperature = TemperatureDto(day = 14.76F, min = 8.89F, max = 16.06F, night = 12.74F, evening = 14.19F, morning = 8.89F), feelsLike = FeelsLikeDto(day = 13.78F, night = 11.66F, evening = 13.21F, morning = 7.93F), pressure = 1021, humidity = 57, dewPoint = 6.44F, windSpeed = 3.93F, windDeg = 35, windGust = 8.24F, weather = listOf(WeatherDetailsDto(id = 500, mainDescription = "Rain", description = "light rain", icon = "10d")), clouds = 96, pop = 0.2F, rain = 0.13F, uvIndex = 1.0F, snow = null)
        val dailyForecasts = listOf(today, tomorrow, day3, day4, day5, day6, day7, day8)
        val weatherDto = WeatherDto(latitude = latitude, longitude = longitude, timezone = "Europe/Kiev", timezoneOffset = 10800, current = currentDto, dailyForecasts = dailyForecasts)

        val todayDomain = DailyDomainModel(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64F, temperature = TemperatureDomainModel(day = 15.07F, min = 9.34F, max = 17.26F, night = 11.26F, evening = 12.31F, morning = 9.34F), feelsLike = FeelsLikeDomainModel(day = 14.43F, night = 10.9F, evening = 11.97F, morning = 8.52F), pressure = 1005, humidity = 69, dewPoint = 9.46F, windSpeed = 6.0F, windDeg = 254, windGust = 8.66F, clouds = 93, pop = 0.57F, rain = 2.39F, uvIndex = 2.34F, snow = null)
        val tomorrowDomain = DailyDomainModel(time = 1663232400, sunriseTime = 1663212562, sunsetTime = 1663258269, moonriseTime = 1663265160, moonsetTime = 1663234260, moonPhase = 0.67F, temperature = TemperatureDomainModel(day = 18.2F, min = 11.13F, max = 20.8F, night = 14.32F, evening = 17.43F, morning = 11.64F), feelsLike = FeelsLikeDomainModel(day = 17.96F, night = 14.31F, evening = 17.24F, morning = 11.31F), pressure = 1004, humidity = 72, dewPoint = 13.16F, windSpeed = 5.45F, windDeg = 210, windGust = 6.75F, clouds = 100, pop = 0.2F, rain = 0.44F, uvIndex = 3.32F, snow = null)
        val day3Domain = DailyDomainModel(time = 1663318800, sunriseTime = 1663299057, sunsetTime = 1663344530, moonriseTime = 1663353120, moonsetTime = 1663325160, moonPhase = 0.7F, temperature = TemperatureDomainModel(day = 15.62F, min = 12.63F, max = 20.5F, night = 14.1F, evening = 16.95F, morning = 12.63F), feelsLike = FeelsLikeDomainModel(day = 15.54F, night = 13.24F, evening = 16.42F, morning = 12.51F), pressure = 1003, humidity = 88, dewPoint = 13.51F, windSpeed = 5.91F, windDeg = 266, windGust = 8.47F, clouds = 100, pop = 0.22F, rain = 0.37F, uvIndex = 3.22F, snow = null)
        val day4Domain = DailyDomainModel(time = 1663405200, sunriseTime = 1663385552, sunsetTime = 1663430790, moonriseTime = 1663441500, moonsetTime = 1663415700, moonPhase = 0.73F, temperature = TemperatureDomainModel(day = 16.87F, min = 11.16F, max = 16.87F, night = 12.45F, evening = 13.06F, morning = 11.88F), feelsLike = FeelsLikeDomainModel(day = 16.07F, night = 11.89F, evening = 12.51F, morning = 10.95F), pressure = 1006, humidity = 56, dewPoint = 8.17F, windSpeed = 4.34F, windDeg = 215, windGust = 4.47F, clouds = 100, pop = 0.17F, uvIndex = 2.53F, rain = null, snow = null)
        val day5Domain = DailyDomainModel(time = 1663491600, sunriseTime = 1663472047, sunsetTime = 1663517051, moonriseTime = 1663530540, moonsetTime = 1663505760, moonPhase = 0.75F, temperature = TemperatureDomainModel(day = 11.49F, min = 9.91F, max = 18.69F, night = 9.91F, evening = 18.69F, morning = 11.99F), feelsLike = FeelsLikeDomainModel(day = 11.12F, night = 6.42F, evening = 18.91F, morning = 11.23F), pressure = 999, humidity = 93, dewPoint = 10.26F, windSpeed = 10.2F, windDeg = 163, windGust = 17.1F, clouds = 100, pop = 1.0F, rain = 10.49F, uvIndex = 0.24F, snow = null)
        val day6Domain = DailyDomainModel(time = 1663578000, sunriseTime = 1663558542, sunsetTime = 1663603311, moonriseTime = 1663620300, moonsetTime = 1663595100, moonPhase = 0.79F, temperature = TemperatureDomainModel(day = 13.92F, min = 8.35F, max = 15.78F, night = 9.08F, evening = 13.64F, morning = 9.3F), feelsLike = FeelsLikeDomainModel(day = 12.88F, night = 6.88F, evening = 12.65F, morning = 5.28F), pressure = 1007, humidity = 58, dewPoint = 5.73F, windSpeed = 10.52F, windDeg = 249, windGust = 17.94F, clouds = 5, pop = 0.23F, rain = 0.19F, uvIndex = 1.0F, snow = null)
        val day7Domain = DailyDomainModel(time = 1663664400, sunriseTime = 1663645037, sunsetTime = 1663689572, moonriseTime = 0, moonsetTime = 1663683840, moonPhase = 0.82F, temperature = TemperatureDomainModel(day = 11.98F, min = 7.19F, max = 12.35F, night = 11.08F, evening = 12.35F, morning = 7.19F), feelsLike = FeelsLikeDomainModel(day = 11.24F, night = 10.46F, evening = 11.73F, morning = 4.28F), pressure = 1014, humidity = 77, dewPoint = 8.06F, windSpeed = 6.64F, windDeg = 257, windGust = 11.41F, clouds = 100, pop = 0.27F, rain = 0.12F, uvIndex = 1.0F, snow = null)
        val day8Domain = DailyDomainModel(time = 1663750800, sunriseTime = 1663731533, sunsetTime = 1663775832, moonriseTime = 1663710600, moonsetTime = 1663771920, moonPhase = 0.85F, temperature = TemperatureDomainModel(day = 14.76F, min = 8.89F, max = 16.06F, night = 12.74F, evening = 14.19F, morning = 8.89F), feelsLike = FeelsLikeDomainModel(day = 13.78F, night = 11.66F, evening = 13.21F, morning = 7.93F), pressure = 1021, humidity = 57, dewPoint = 6.44F, windSpeed = 3.93F, windDeg = 35, windGust = 8.24F, clouds = 96, pop = 0.2F, rain = 0.13F, uvIndex = 1.0F, snow = null)
        val currentWeather = CurrentWeatherDomainModel(currentTime = 1663109294, sunriseTime = 1663126067, sunsetTime = 1663172008, temperature = 9.69F, feelsLike = 8.63F, pressure = 1006, humidity = 94, dewPoint = 8.77F, uvIndex = 0.0F, clouds = 100, visibility = 10000, windSpeed = 2.26F, windDeg = 317, windGust = 5.95F, weatherDetails = WeatherDetailsDomainModel(id = 500, mainDescription = "Rain", detailedDescription = "light rain", iconUrl = "https://openweathermap.org/img/wn/10n@2x.png"), lastHourRainVolume = 0.12F, lastHourSnowVolume = null)

        every { currentDtoMapper.mapToDomainModel(currentDto) } returns currentWeather
        every { dailyDtoMapper.mapToDomainModel(today) } returns todayDomain
        every { dailyDtoMapper.mapToDomainModel(tomorrow) } returns tomorrowDomain
        every { dailyDtoMapper.mapToDomainModel(day3) } returns day3Domain
        every { dailyDtoMapper.mapToDomainModel(day4) } returns day4Domain
        every { dailyDtoMapper.mapToDomainModel(day5) } returns day5Domain
        every { dailyDtoMapper.mapToDomainModel(day6) } returns day6Domain
        every { dailyDtoMapper.mapToDomainModel(day7) } returns day7Domain
        every { dailyDtoMapper.mapToDomainModel(day8) } returns day8Domain

        val expectedWeatherDomainModel = WeatherDomainModel(latitude = latitude, longitude = longitude, timezone = "Europe/Kiev", timezoneOffset = 10800, currentWeather = currentWeather, dailyForecasts = listOf(todayDomain, tomorrowDomain, day3Domain, day4Domain, day5Domain, day6Domain, day7Domain, day8Domain))

        val actualWeatherDomainModel = weatherDtoMapper.mapToDomainModel(weatherDto)

        Assert.assertEquals(expectedWeatherDomainModel, actualWeatherDomainModel)
    }
}
