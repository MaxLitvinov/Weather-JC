package com.jc.weather.ip_api

import com.jc.weather.api_client.di.factory.network_response.NetworkResponse
import com.jc.weather.ip_api.data.api.IpApi
import com.jc.weather.ip_api.data.dto.IpDto
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainResult
import com.jc.weather.ip_api.domain.repository.IpRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

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
    fun `Check fetch location returns success`() = runBlocking {
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

        val response = mockk<NetworkResponse.Success<IpDto, Any>> {
            every { body } returns IpDto(
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
        }

        coEvery { api.fetchLocation() } returns response

        val expectedResult = IpDomainResult.Success(
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

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Check fetch location returns server error`() = runBlocking {
        val errorCode = 500
        val errorMessage = "Server error message"

        val response = mockk<NetworkResponse.ServerError<IpDto, Any>> {
            every { code } returns errorCode
            every { error } returns Exception(errorMessage)
        }

        coEvery { api.fetchLocation() } returns response

        val actualResult = ipRepository.fetchLocation()

        val expectedErrorMessage = "Server error code - $errorCode, message - $errorMessage"
        val expectedResult = IpDomainResult.Failure(expectedErrorMessage)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Check fetch location returns network error`() = runBlocking {
        val errorBody: Any? = null
        val errorMessage = "Network error message"

        val response = mockk<NetworkResponse.NetworkError<IpDto, Any>> {
            every { body } returns errorBody
            every { error } returns IOException(errorMessage)
        }

        coEvery { api.fetchLocation() } returns response

        val actualResult = ipRepository.fetchLocation()

        val expectedErrorMessage = "Network error body - $errorBody, message - $errorMessage"
        val expectedResult = IpDomainResult.Failure(expectedErrorMessage)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Check fetch location returns socket timeout exception`() = runBlocking {
        val errorBody: Any? = null
        val errorMessage = "failed to connect to ip-api.com/208.95.112.1 (port 80) from /192.168.0.21 (port 42554) after 15000ms"

        val response = mockk<NetworkResponse.NetworkError<IpDto, Any>> {
            every { body } returns errorBody
            every { error } returns SocketTimeoutException(errorMessage)
        }

        coEvery { api.fetchLocation() } returns response

        val actualResult = ipRepository.fetchLocation()

        val expectedErrorMessage = "Network error body - $errorBody, message - $errorMessage"
        val expectedResult = IpDomainResult.Failure(expectedErrorMessage)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Check fetch location returns unknown error`() = runBlocking {
        val errorCode = 500

        val response = mockk<NetworkResponse.UnknownError<IpDto, Any>> {
            every { code } returns errorCode
            every { error } returns Exception("Unknown error message")
        }

        coEvery { api.fetchLocation() } returns response

        val actualResult = ipRepository.fetchLocation()

        val expectedErrorMessage = "Unknown error code - $errorCode"
        val expectedResult = IpDomainResult.Failure(expectedErrorMessage)

        assertEquals(expectedResult, actualResult)
    }
}
