package com.jc.weather.ip_api

import com.jc.weather.ip_api.data.dto.IpDto
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IpDtoMapperTest {

    private lateinit var mapper: IpDtoMapper

    @Before
    fun setup() {
        mapper = IpDtoMapper()
    }

    @Test
    fun `Check IpDto mapping`() {
        val query = "31.14.75.23"
        val status = "success"
        val country = "Ukraine"
        val countryCode = "UA"
        val region = "30"
        val regionName = "Kyiv City"
        val city = "Kyiv"
        val zip = "01021"
        val lat = 50.458
        val lon = 30.5303
        val timezone = "Europe/Kiev"
        val isp = "PRCDN"
        val org = ""
        val _as = "AS212238 Datacamp Limited"

        val dto = IpDto(
            query = query,
            status = status,
            country = country,
            countryCode = countryCode,
            region = region,
            regionName = regionName,
            city = city,
            zip = zip,
            lat = lat,
            lon = lon,
            timezone = timezone,
            isp = isp,
            org = org,
            _as = _as
        )

        val expectedDomainModel = IpDomainModel(
            query = query,
            status = status,
            country = country,
            countryCode = countryCode,
            region = region,
            regionName = regionName,
            city = city,
            zip = zip,
            lat = lat,
            lon = lon,
            timezone = timezone,
            isp = isp,
            org = org,
            _as = _as
        )

        val actualDomainModel = mapper.mapToDomain(dto)

        Assert.assertEquals(expectedDomainModel, actualDomainModel)
    }
}
