package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.HourlyDto
import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.domain.mapper.HourlyDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.WeatherDetailsDtoMapper
import com.jc.weather.open_weather_map.domain.model.HourlyDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HourlyDtoMapperTest {

    private lateinit var weatherMapper: WeatherDetailsDtoMapper
    private lateinit var hourlyDtoMapper: HourlyDtoMapper

    @Before
    fun setup() {
        weatherMapper = mockk()
        hourlyDtoMapper = HourlyDtoMapper(weatherMapper)
    }

    @Test
    fun `Check hourly DTO mapping to domain model`() {
        val weatherId = 804
        val weatherMainDescription = "Clouds"
        val weatherDescription = "overcast clouds"
        val weatherIcon = "04d"
        val weatherDetailsDto = WeatherDetailsDto(
            id = weatherId,
            mainDescription = weatherMainDescription,
            description = weatherDescription,
            icon = weatherIcon
        )

        val time = 1664974800L
        val dto = HourlyDto(
            time = 1664974800L,
            temperature = 11F,
            feelsLike = 10.22F,
            pressure = 1022,
            humidity = 79,
            dewPoint = 7.5F,
            uvIndex = 0.45F,
            clouds = 100,
            visibility = 10000,
            windSpeed = 4.61F,
            windDeg = 275,
            windGust = 8.45F,
            weather = listOf(weatherDetailsDto),
            pop = 0F
        )

        val weatherDetailsDomainModel = WeatherDetailsDomainModel(
            id = weatherId,
            mainDescription = weatherMainDescription,
            detailedDescription = weatherDescription,
            iconUrl = weatherIcon
        )
        every { weatherMapper.mapToDomainModel(weatherDetailsDto) } returns weatherDetailsDomainModel

        val timezoneOffset = 10800
        val actualModel = hourlyDtoMapper.mapToDomainModel(timezoneOffset, dto)

        val expectedTime = time + timezoneOffset
        val expectedModel = HourlyDomainModel(
            time = expectedTime,
            temperature = 11F,
            feelsLike = 10.22F,
            pressure = 1022,
            humidity = 79,
            dewPoint = 7.5F,
            uvIndex = 0.45F,
            clouds = 100,
            visibility = 10000,
            windSpeed = 4.61F,
            windDeg = 275,
            windGust = 8.45F,
            weather = WeatherDetailsDomainModel(
                id = weatherId,
                mainDescription = weatherMainDescription,
                detailedDescription = weatherDescription,
                iconUrl = weatherIcon
            ),
            pop = 0F
        )

        assertEquals(expectedModel, actualModel)
    }
}
