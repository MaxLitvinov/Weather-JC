package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.DailyDto
import com.jc.weather.open_weather_map.data.dto.FeelsLikeDto
import com.jc.weather.open_weather_map.data.dto.TemperatureDto
import com.jc.weather.open_weather_map.data.dto.WeatherDetailsDto
import com.jc.weather.open_weather_map.domain.mapper.DailyDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.FeelsLikeDtoMapper
import com.jc.weather.open_weather_map.domain.mapper.TemperatureDtoMapper
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DailyDtoMapperTest {

    private lateinit var temperatureDtoMapper: TemperatureDtoMapper
    private lateinit var feelsLikeDtoMapper: FeelsLikeDtoMapper
    private lateinit var dailyDtoMapper: DailyDtoMapper

    @Before
    fun setup() {
        temperatureDtoMapper = mockk()
        feelsLikeDtoMapper = mockk()
        dailyDtoMapper = DailyDtoMapper(temperatureDtoMapper, feelsLikeDtoMapper)
    }

    @Test
    fun `Check dailyDto mapping`() {
        val dayTemperature = 15.07
        val minTemperature = 9.34
        val maxTemperature = 17.26
        val nightTemperature = 11.26
        val eveningTemperature = 12.31
        val morningTemperature = 9.34

        val dayTemperatureFeelsLike = 14.43
        val nightTemperatureFeelsLike = 10.9
        val eveningTemperatureFeelsLike = 11.9
        val morningTemperatureFeelsLike = 8.52

        val temperatureDto = TemperatureDto(day = dayTemperature, min = minTemperature, max = maxTemperature, night = nightTemperature, evening = eveningTemperature, morning = morningTemperature)
        val feelsLikeDto = FeelsLikeDto(day = dayTemperatureFeelsLike, night = nightTemperatureFeelsLike, evening = eveningTemperatureFeelsLike, morning = morningTemperatureFeelsLike)
        val dailyDto = DailyDto(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64, temperature = temperatureDto, feelsLike = feelsLikeDto, pressure = 1005, humidity = 69, dewPoint = 9.46, windSpeed = 6.0, windDeg = 254, windGust = 8.66, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 93, pop = 0.57, rain = 2.39, uvIndex = 2.34, snow = null)

        val temperatureDomainModel = TemperatureDomainModel(day = 15.07, min = 9.34, max = 17.26, night = 11.26, evening = 12.31, morning = 9.34)
        val feelsLikeDomainModel = FeelsLikeDomainModel(day = 14.43, night = 10.9, evening = 11.97, morning = 8.52)

        every { temperatureDtoMapper.mapToDomainModel(temperatureDto) } returns temperatureDomainModel
        every { feelsLikeDtoMapper.mapToDomainModel(feelsLikeDto) } returns feelsLikeDomainModel

        val expectedDomainModel = DailyDomainModel(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64, temperature = temperatureDomainModel, feelsLike = feelsLikeDomainModel, pressure = 1005, humidity = 69, dewPoint = 9.46, windSpeed = 6.0, windDeg = 254, windGust = 8.66, clouds = 93, pop = 0.57, rain = 2.39, uvIndex = 2.34, snow = null)

        val actualDomainModel = dailyDtoMapper.mapToDomainModel(dailyDto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
