package com.jc.weather.pages.home

import android.content.Context
import com.jc.weather.home_page.core.HomePageInteractor
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.ip_api.domain.model.IpDomainModel
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.domain.repository.WeatherForecastRepository
import com.jc.weather.time.TimestampProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomePageInteractorTest {

    private lateinit var context: Context
    private lateinit var timestampProvider: TimestampProvider
    private lateinit var weatherForecastRepository: WeatherForecastRepository
    private lateinit var ipRepository: IpRepository
    private lateinit var weatherDataStoreRepository: WeatherDataStoreRepository
    private lateinit var weatherDomainModelMapper: WeatherDomainModelMapper
    private lateinit var interactor: HomePageInteractor

    @Before
    fun setup() {
        context = mockk()
        timestampProvider = mockk()
        weatherForecastRepository = mockk()
        ipRepository = mockk()
        weatherDomainModelMapper = mockk()
        interactor = HomePageInteractor(
            context,
            timestampProvider,
            weatherForecastRepository,
            ipRepository,
            weatherDataStoreRepository,
            weatherDomainModelMapper
        )
    }

    // TODO: Fix test
    @Test
    fun `When fetch weather returns success`() = runBlocking {
        val latitude = 33.8743
        val longitude = -84.4653
        val ipDomainModel = IpDomainModel(
            query = "66.115.181.94",
            status = "success",
            country = "United States",
            countryCode = "US",
            region = "GA",
            regionName = "Georgia",
            city = "Atlanta",
            zip = "30339",
            lat = latitude,
            lon = longitude,
            timezone = "America/New_York",
            isp = "Performive LLC",
            org = "Performive LLC",
            _as = "AS46562 Performive LLC",
        )
        coEvery { ipRepository.fetchLocation() } returns ipDomainModel

        val weatherDomainModel: WeatherDomainModel = mockk()
        coEvery { weatherForecastRepository.fetchWeather("$latitude", "$longitude") } returns weatherDomainModel

        val weatherModel: WeatherModel = mockk()
        every { weatherDomainModel.currentWeather.currentTime } returns 1664896591L
        every { weatherDomainModel.timezoneOffset } returns -14400
        every { weatherDomainModelMapper.mapToUiModel(weatherDomainModel) } returns weatherModel

        val actualUiState = interactor.fetchWeather()

        coVerify { weatherDataStoreRepository.saveData(weatherDomainModel) }
    }
}
