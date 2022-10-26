package com.jc.weather.open_weather_map

import com.jc.weather.open_weather_map.data.dto.FeelsLikeDto
import com.jc.weather.open_weather_map.domain.mapper.FeelsLikeDtoMapper
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FeelsListDtoMapperTest {

    private lateinit var mapper: FeelsLikeDtoMapper

    @Before
    fun setup() {
        mapper = FeelsLikeDtoMapper()
    }

    @Test
    fun `Check FeelsLikeDto mapping`() {
        val day = 14.43F
        val night = 10.9F
        val evening = 11.97F
        val morning = 8.52F

        val dto = FeelsLikeDto(day = day, night = night, evening = evening, morning = morning)

        val expectedDomainModel = FeelsLikeDomainModel(day = day, night = night, evening = evening, morning = morning)

        val actualDomainModel = mapper.mapToDomainModel(dto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
