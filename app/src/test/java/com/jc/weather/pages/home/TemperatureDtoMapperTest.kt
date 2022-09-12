package com.jc.weather.pages.home

import com.jc.weather.data.open_weather_map.TemperatureDto
import com.jc.weather.domain.open_weather_map.mapper.TemperatureDtoMapper
import com.jc.weather.domain.open_weather_map.model.TemperatureDomainModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TemperatureDtoMapperTest {

    private lateinit var mapper: TemperatureDtoMapper

    @Before
    fun setup() {
        mapper = TemperatureDtoMapper()
    }

    @Test
    fun `Check TemperatureDto mapping`() {
        val morning = 11.64
        val day = 18.2
        val evening = 17.43
        val night = 14.32
        val min = 11.13
        val max = 20.8

        val dto = TemperatureDto(morning = morning, day = day, evening = evening, night = night, min = min, max = max)

        val expectedDomainModel = TemperatureDomainModel(morning = morning, day = day, evening = evening, night = night, min = min, max = max)

        val actualDomainModel = mapper.mapToDomainModel(dto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
