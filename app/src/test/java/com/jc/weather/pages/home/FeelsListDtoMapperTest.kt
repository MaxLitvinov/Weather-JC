package com.jc.weather.pages.home

import com.jc.weather.data.open_weather_map.FeelsLikeDto
import com.jc.weather.domain.open_weather_map.mapper.FeelsLikeDtoMapper
import com.jc.weather.domain.open_weather_map.model.FeelsLikeDomainModel
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
        val day = 14.43
        val night = 10.9
        val evening = 11.97
        val morning = 8.52

        val dto = FeelsLikeDto(day = day, night = night, evening = evening, morning = morning)

        val expectedDomainModel = FeelsLikeDomainModel(day = day, night = night, evening = evening, morning = morning)

        val actualDomainModel = mapper.mapToDomainModel(dto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
