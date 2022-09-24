package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.TemperatureDto
import com.jc.weather.open_weather_map.domain.mapper.TemperatureDtoMapper
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
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
