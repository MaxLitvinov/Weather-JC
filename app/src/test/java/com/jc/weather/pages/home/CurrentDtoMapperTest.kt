package com.jc.weather.pages.home

import com.jc.weather.data.open_weather_map.CurrentDto
import com.jc.weather.data.open_weather_map.RainDto
import com.jc.weather.data.open_weather_map.WeatherDetailsDto
import com.jc.weather.domain.open_weather_map.mapper.CurrentDtoMapper
import com.jc.weather.domain.open_weather_map.mapper.WeatherDetailsDtoMapper
import com.jc.weather.domain.open_weather_map.model.CurrentWeatherDomainModel
import com.jc.weather.domain.open_weather_map.model.WeatherDetailsDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrentDtoMapperTest {

    private lateinit var weatherDetailsDtoMapper: WeatherDetailsDtoMapper
    private lateinit var currentDtoMapper: CurrentDtoMapper

    @Before
    fun setup() {
        weatherDetailsDtoMapper = mockk()
        currentDtoMapper = CurrentDtoMapper(weatherDetailsDtoMapper)
    }

    @Test
    fun `Check CurrentDto mapping`() {
        val currentTime: Long = 1663109294
        val sunriseTime: Long = 1663126067
        val sunsetTime: Long = 1663172008
        val temperature = 9.69
        val feelsLike = 8.63
        val pressure = 1006
        val humidity = 94
        val dewPoint = 8.77
        val uvIndex = 0.0
        val clouds = 100
        val visibility = 10000
        val windSpeed = 2.26
        val windDeg = 317
        val windGust = 5.95
        val rainLastHourVolume = 0.12
        val snowLastHourVolume: Double? = null

        val weatherDetailId = 500
        val weatherDetailMainDescription = "Rain"
        val weatherDetailDescription = "light rain"
        val weatherDetailIcon = "10n"

        val currentDto = CurrentDto(currentTime = currentTime, sunriseTime = sunriseTime, sunsetTime = sunsetTime, temperature = temperature, feelsLike = feelsLike, pressure = pressure, humidity = humidity, dewPoint = dewPoint, uvIndex = uvIndex, clouds = clouds, visibility = visibility, windSpeed = windSpeed, windDeg = windDeg, windGust = windGust, weather = listOf(WeatherDetailsDto(id = weatherDetailId, mainDescription = weatherDetailMainDescription, description = weatherDetailDescription, icon = weatherDetailIcon)), rain = RainDto(lastHourVolume = rainLastHourVolume), snow = null)

        val weatherDetailsDomainModel = WeatherDetailsDomainModel(id = weatherDetailId, mainDescription = weatherDetailMainDescription, detailedDescription = weatherDetailDescription, icon = weatherDetailIcon)

        every { weatherDetailsDtoMapper.mapToDomainModel(currentDto.weather.first()) } returns weatherDetailsDomainModel

        val expectedCurrentWeatherDomainModel = CurrentWeatherDomainModel(currentTime = currentTime, sunriseTime = sunriseTime, sunsetTime = sunsetTime, temperature = temperature, feelsLike = feelsLike, pressure = pressure, humidity = humidity, dewPoint = dewPoint, clouds = clouds, uvIndex = uvIndex, visibility = visibility, windDeg = windDeg, windGust = windGust, windSpeed = windSpeed, lastHourRainVolume = rainLastHourVolume, lastHourSnowVolume = snowLastHourVolume, weatherDetails = weatherDetailsDomainModel)

        val actualCurrentWeatherDomainModel = currentDtoMapper.mapToDomainModel(currentDto)

        Assert.assertEquals(expectedCurrentWeatherDomainModel, actualCurrentWeatherDomainModel)
    }
}
