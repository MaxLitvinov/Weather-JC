package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.domain.mapper.WeatherDetailsDtoMapper
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
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
        val weatherDetailIconName = "10n"
        val weatherDetailIconUrl = "https://openweathermap.org/img/wn/$weatherDetailIconName@2x.png"

        val weatherDetailsDto = WeatherDetailsDto(id = weatherDetailId, mainDescription = weatherDetailMainDescription, description = weatherDetailDescription, icon = weatherDetailIconName)

        val expectedWeatherDetailsDomainModel = WeatherDetailsDomainModel(id = weatherDetailId, mainDescription = weatherDetailMainDescription, detailedDescription = weatherDetailDescription, iconUrl = weatherDetailIconUrl)

        val actualWeatherDetailsDomainModel = weatherDetailsDtoMapper.mapToDomainModel(weatherDetailsDto)

        Assert.assertEquals(expectedWeatherDetailsDomainModel, actualWeatherDetailsDomainModel)
    }
}
