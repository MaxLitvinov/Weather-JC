package com.jc.weather.pages.home

import com.jc.weather.data.open_weather_map.WeatherDetailsDto
import com.jc.weather.domain.open_weather_map.mapper.WeatherDetailsDtoMapper
import com.jc.weather.domain.open_weather_map.model.WeatherDetailsDomainModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherDetailsDtoMapperTest {

    private lateinit var weatherDetailsDtoMapper: WeatherDetailsDtoMapper

    @Before
    fun setup() {
        weatherDetailsDtoMapper = WeatherDetailsDtoMapper()
    }

    @Test
    fun `Check WeatherDetailsDto mapping`() {
        val weatherDetailId = 500
        val weatherDetailMainDescription = "Rain"
        val weatherDetailDescription = "light rain"
        val weatherDetailIcon = "10n"

        val weatherDetailsDto = WeatherDetailsDto(id = weatherDetailId, mainDescription = weatherDetailMainDescription, description = weatherDetailDescription, icon = weatherDetailIcon)

        val expectedWeatherDetailsDomainModel = WeatherDetailsDomainModel(id = weatherDetailId, mainDescription = weatherDetailMainDescription, detailedDescription = weatherDetailDescription, icon = weatherDetailIcon)

        val actualWeatherDetailsDomainModel = weatherDetailsDtoMapper.mapToDomainModel(weatherDetailsDto)

        Assert.assertEquals(expectedWeatherDetailsDomainModel, actualWeatherDetailsDomainModel)
    }
}
