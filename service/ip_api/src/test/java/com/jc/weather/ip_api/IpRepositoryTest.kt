package com.jc.weather.ip_api

import com.jc.weather.api_client.di.factory.network_response.NetworkResponse
import com.jc.weather.ip_api.data.api.IpApi
import com.jc.weather.ip_api.data.dto.IpDto
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainResult
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.logger.Logger
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException
import java.net.SocketTimeoutException

@RunWith(Enclosed::class)
class IpRepositoryTest {

    class NonParameterisedTests : BaseTest() {

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
    }

    abstract class BaseTest {

        lateinit var api: IpApi
        lateinit var mapper: IpDtoMapper
        lateinit var ipRepository: IpRepository
        lateinit var logger: Logger

        @Before
        fun setup() {
            api = mockk()
            mapper = IpDtoMapper()
            logger = mockk()
            ipRepository = IpRepository(api, mapper, logger)
        }
    }

    @RunWith(Parameterized::class)
    class ParameterizedTest(
        val testName: String,
        val testCase: TestCase
    ) : BaseTest() {

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data() = listOf(
                arrayOf(
                    "Check fetch location returns server error",
                    TestCase(
                        errorBody = null,
                        errorCode = 500,
                        errorMessage = "Server error message",
                        fullErrorMessage = "Server error code - 500, message - Server error message",
                        networkResponse = mockk<NetworkResponse.ServerError<IpDto, Any>> {
                            every { code } returns 500
                            every { error } returns Exception("Server error message")
                        }
                    )
                ),
                arrayOf(
                    "Check fetch location returns network error",
                    TestCase(
                        errorBody = null,
                        errorCode = null,
                        errorMessage = "Network error message",
                        fullErrorMessage = "Network error body - null, message - Network error message",
                        networkResponse = mockk<NetworkResponse.NetworkError<IpDto, Any>> {
                            every { body } returns null
                            every { error } returns IOException("Network error message")
                        }
                    )
                ),
                arrayOf(
                    "Check fetch location returns socket timeout exception",
                    TestCase(
                        errorBody = null,
                        errorCode = null,
                        errorMessage = "failed to connect to ip-api.com/208.95.112.1 (port 80) from /192.168.0.21 (port 42554) after 15000ms",
                        fullErrorMessage = "Network error body - null, message - failed to connect to ip-api.com/208.95.112.1 (port 80) from /192.168.0.21 (port 42554) after 15000ms",
                        networkResponse = mockk<NetworkResponse.NetworkError<IpDto, Any>> {
                            every { body } returns null
                            every { error } returns SocketTimeoutException("failed to connect to ip-api.com/208.95.112.1 (port 80) from /192.168.0.21 (port 42554) after 15000ms")
                        }
                    )
                ),
                arrayOf(
                    "Check fetch location returns unknown error",
                    TestCase(
                        errorBody = null,
                        errorCode = 500,
                        errorMessage = "Unknown error message",
                        fullErrorMessage = "Unknown error code - 500",
                        networkResponse = mockk<NetworkResponse.UnknownError<IpDto, Any>> {
                            every { code } returns 500
                            every { error } returns Exception("Unknown error message")
                        }
                    )
                )
            )
        }

        @Test
        fun test() = runBlocking {
            coEvery { api.fetchLocation() } returns testCase.networkResponse
            every { logger.report(testCase.fullErrorMessage) } just Runs

            val actualResult = ipRepository.fetchLocation()
            verify { logger.report(testCase.fullErrorMessage) }

            val expectedResult = IpDomainResult.Failure(testCase.fullErrorMessage)

            assertEquals(expectedResult, actualResult)
        }

        data class TestCase(
            val errorBody: Any?,
            val errorCode: Int?,
            val errorMessage: String,
            val fullErrorMessage: String,
            val networkResponse: NetworkResponse<IpDto, Any>
        )
    }
}
