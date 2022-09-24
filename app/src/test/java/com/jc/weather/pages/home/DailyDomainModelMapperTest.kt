package com.jc.weather.pages.home

import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import com.jc.weather.pages.home.mapper.DailyDomainModelMapper
import com.jc.weather.pages.home.model.DayForecast
import com.jc.weather.time.TimestampProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DailyDomainModelMapperTest {

    private lateinit var timestampProvider: TimestampProvider
    private lateinit var dailyDomainModelMapper: DailyDomainModelMapper

    @Before
    fun setup() {
        timestampProvider = mockk()
        dailyDomainModelMapper = DailyDomainModelMapper(timestampProvider)
    }

    @Test
    fun `Check domain model mapping into UI model`() {
        val domainModel = DailyDomainModel(
            time = 1663146000,
            sunriseTime = 1663126067,
            sunsetTime = 1663172008,
            moonriseTime = 1663177560,
            moonsetTime = 1663143240,
            moonPhase = 0.64,
            temperature = TemperatureDomainModel(
                day = 15.07,
                min = 9.34,
                max = 17.26,
                night = 11.26,
                evening = 12.31,
                morning = 9.34
            ),
            feelsLike = FeelsLikeDomainModel(
                day = 14.43,
                night = 10.9,
                evening = 11.97,
                morning = 8.52
            ),
            pressure = 1005,
            humidity = 69,
            dewPoint = 9.46,
            windSpeed = 6.0,
            windDeg = 254,
            windGust = 8.66,
            clouds = 93,
            pop = 0.57,
            rain = 2.39,
            uvIndex = 2.34,
            snow = null
        )

        val dayName = "14.09 Wednesday"
        every { timestampProvider.toDayMonthAndDayName(domainModel.time) } returns dayName

        val actualUiModel = dailyDomainModelMapper.mapToUiModel(domainModel)

        val expectedUiModel = DayForecast(
            dayName = dayName,
            morning = 9.34,
            day = 15.07,
            evening = 12.31,
            night = 11.26,
            min = 9.34,
            max = 17.26
        )

        assertEquals(expectedUiModel, actualUiModel)
    }
}
