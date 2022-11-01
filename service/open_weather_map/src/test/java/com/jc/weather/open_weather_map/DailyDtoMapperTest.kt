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
        val dayTemperature = 15.07f
        val minTemperature = 9.34f
        val maxTemperature = 17.26f
        val nightTemperature = 11.26f
        val eveningTemperature = 12.31f
        val morningTemperature = 9.34f

        val dayTemperatureFeelsLike = 14.43f
        val nightTemperatureFeelsLike = 10.9f
        val eveningTemperatureFeelsLike = 11.9f
        val morningTemperatureFeelsLike = 8.52f

        val temperatureDto = TemperatureDto(day = dayTemperature, min = minTemperature, max = maxTemperature, night = nightTemperature, evening = eveningTemperature, morning = morningTemperature)
        val feelsLikeDto = FeelsLikeDto(day = dayTemperatureFeelsLike, night = nightTemperatureFeelsLike, evening = eveningTemperatureFeelsLike, morning = morningTemperatureFeelsLike)
        val dailyDto = DailyDto(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64F, temperature = temperatureDto, feelsLike = feelsLikeDto, pressure = 1005, humidity = 69, dewPoint = 9.46F, windSpeed = 6.0F, windDeg = 254, windGust = 8.66F, weather = listOf(WeatherDetailsDto(id = 501, mainDescription = "Rain", description = "moderate rain", icon = "10d")), clouds = 93, pop = 0.57F, rain = 2.39F, uvIndex = 2.34F, snow = null)

        val temperatureDomainModel = TemperatureDomainModel(day = 15.07F, min = 9.34F, max = 17.26F, night = 11.26F, evening = 12.31F, morning = 9.34F)
        val feelsLikeDomainModel = FeelsLikeDomainModel(day = 14.43F, night = 10.9F, evening = 11.97F, morning = 8.52F)

        every { temperatureDtoMapper.mapToDomainModel(temperatureDto) } returns temperatureDomainModel
        every { feelsLikeDtoMapper.mapToDomainModel(feelsLikeDto) } returns feelsLikeDomainModel

        val timezoneOffset = 10800
        val time = 1663146000L + timezoneOffset
        val expectedDomainModel = DailyDomainModel(time = time, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64F, temperature = temperatureDomainModel, feelsLike = feelsLikeDomainModel, pressure = 1005, humidity = 69, dewPoint = 9.46F, windSpeed = 6.0F, windDeg = 254, windGust = 8.66F, clouds = 93, pop = 0.57F, rain = 2.39F, uvIndex = 2.34F, snow = null)

        val actualDomainModel = dailyDtoMapper.mapToDomainModel(timezoneOffset, dailyDto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
