package com.jc.weather.ip_api

import com.jc.weather.ip_api.data.api.IpApi
import com.jc.weather.ip_api.data.dto.IpDto
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainModel
import com.jc.weather.ip_api.domain.repository.IpRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IpRepositoryTest {

    private lateinit var api: IpApi
    private lateinit var mapper: IpDtoMapper
    private lateinit var ipRepository: IpRepository

    @Before
    fun setup() {
        api = mockk()
        mapper = IpDtoMapper()
        ipRepository = IpRepository(api, mapper)
    }

    @Test
    fun `Check fetch location`() = runBlocking {
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

        val ipDtoModel = IpDto(
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

        coEvery { api.fetchLocation() } returns ipDtoModel

        val expectedResult = IpDomainModel(
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

        val actualResult = ipRepository.fetchLocation()

        Assert.assertEquals(expectedResult, actualResult)
    }
}
