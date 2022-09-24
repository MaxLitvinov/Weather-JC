package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.CurrentDto
import com.jc.weather.open_weather_map.data.dto.RainDto
import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.domain.mapper.CurrentDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.WeatherDetailsDtoMapper
import com.jc.weather.open_weather_map.domain.model.CurrentWeatherDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
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
        val weatherDetailIconName = "10n"
        val weatherDetailIconUrl = "https://openweathermap.org/img/wn/$weatherDetailIconName@2x.png"

        val currentDto = CurrentDto(currentTime = currentTime, sunriseTime = sunriseTime, sunsetTime = sunsetTime, temperature = temperature, feelsLike = feelsLike, pressure = pressure, humidity = humidity, dewPoint = dewPoint, uvIndex = uvIndex, clouds = clouds, visibility = visibility, windSpeed = windSpeed, windDeg = windDeg, windGust = windGust, weather = listOf(WeatherDetailsDto(id = weatherDetailId, mainDescription = weatherDetailMainDescription, description = weatherDetailDescription, icon = weatherDetailIconName)), rain = RainDto(lastHourVolume = rainLastHourVolume), snow = null)

        val weatherDetailsDomainModel = WeatherDetailsDomainModel(id = weatherDetailId, mainDescription = weatherDetailMainDescription, detailedDescription = weatherDetailDescription, iconUrl = weatherDetailIconUrl)

        every { weatherDetailsDtoMapper.mapToDomainModel(currentDto.weather.first()) } returns weatherDetailsDomainModel

        val expectedCurrentWeatherDomainModel = CurrentWeatherDomainModel(currentTime = currentTime, sunriseTime = sunriseTime, sunsetTime = sunsetTime, temperature = temperature, feelsLike = feelsLike, pressure = pressure, humidity = humidity, dewPoint = dewPoint, clouds = clouds, uvIndex = uvIndex, visibility = visibility, windDeg = windDeg, windGust = windGust, windSpeed = windSpeed, lastHourRainVolume = rainLastHourVolume, lastHourSnowVolume = snowLastHourVolume, weatherDetails = weatherDetailsDomainModel)

        val actualCurrentWeatherDomainModel = currentDtoMapper.mapToDomainModel(currentDto)

        Assert.assertEquals(expectedCurrentWeatherDomainModel, actualCurrentWeatherDomainModel)
    }
}
