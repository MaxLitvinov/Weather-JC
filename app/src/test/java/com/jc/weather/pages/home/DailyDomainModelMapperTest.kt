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
            moonPhase = 0.64F,
            temperature = TemperatureDomainModel(
                day = 15.07F,
                min = 9.34F,
                max = 17.26F,
                night = 11.26F,
                evening = 12.31F,
                morning = 9.34F
            ),
            feelsLike = FeelsLikeDomainModel(
                day = 14.43F,
                night = 10.9F,
                evening = 11.97F,
                morning = 8.52F
            ),
            pressure = 1005,
            humidity = 69,
            dewPoint = 9.46F,
            windSpeed = 6.0F,
            windDeg = 254,
            windGust = 8.66F,
            clouds = 93,
            pop = 0.57F,
            rain = 2.39F,
            uvIndex = 2.34F,
            snow = null
        )

        val dayName = "14.09 Wednesday"
        every { timestampProvider.toDayMonthAndDayName(domainModel.time) } returns dayName

        val actualUiModel = dailyDomainModelMapper.mapToUiModel(domainModel)

        val expectedUiModel = DayForecast(
            dayName = dayName,
            morning = 9.34F,
            day = 15.07F,
            evening = 12.31F,
            night = 11.26F,
            min = 9.34F,
            max = 17.26F
        )

        assertEquals(expectedUiModel, actualUiModel)
    }
}
