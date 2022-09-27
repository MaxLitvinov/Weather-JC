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
        val morning = 11.64F
        val day = 18.2F
        val evening = 17.43F
        val night = 14.32F
        val min = 11.13F
        val max = 20.8F

        val dto = TemperatureDto(morning = morning, day = day, evening = evening, night = night, min = min, max = max)

        val expectedDomainModel = TemperatureDomainModel(morning = morning, day = day, evening = evening, night = night, min = min, max = max)

        val actualDomainModel = mapper.mapToDomainModel(dto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
