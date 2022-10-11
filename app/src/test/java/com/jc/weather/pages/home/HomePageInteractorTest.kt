package com.jc.weather.pages.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.home_page.core.HomePageInteractor
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.ip_api.domain.model.IpDomainResult
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.open_weather_map.domain.repository.WeatherForecastRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.IOException

private const val fakeLatitude = 33.8743
private const val fakeLongitude = -84.4653
private const val fakeCity = "Kiev"
private const val fakeIconUrl = "iconUrl"
private const val fakeTemperature = "9.39"
private const val fakeDescription = "Overcast clouds"
private const val fakeError = "Error"

class HomePageInteractorTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var context: Context

    private lateinit var ipRepository: IpRepository
    private lateinit var weatherForecastRepository: WeatherForecastRepository
    private lateinit var weatherDataStoreRepository: WeatherDataStoreRepository
    private lateinit var weatherDomainModelMapper: WeatherDomainModelMapper

    private lateinit var interactor: HomePageInteractor

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        ipRepository = mockk(relaxed = true)
        weatherForecastRepository = mockk(relaxed = true)
        weatherDataStoreRepository = mockk(relaxed = true)
        weatherDomainModelMapper = mockk(relaxed = true)

        interactor = HomePageInteractor(
            context,
            weatherForecastRepository,
            ipRepository,
            weatherDataStoreRepository,
            weatherDomainModelMapper
        )
    }

    @Test
    fun `When fetch weather returns success`() = runBlocking {
        val ipDomainModel = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainModel

        val weatherDomainModel = mockk<WeatherDomainModel>(relaxed = true) {
            every { latitude } returns 33.8743F
            every { longitude } returns -84.4653F
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainModel

        coEvery { weatherDataStoreRepository.saveData(weatherDomainModel) } just Runs

        val weatherUiModel = mockk<WeatherModel>(relaxed = true) {
            every { city } returns fakeCity
            every { iconUrl } returns fakeIconUrl
            every { temperature } returns fakeTemperature
            every { weatherDescription } returns fakeDescription
            every { dailyForecasts } returns listOf()
        }
        every { weatherDomainModelMapper.mapToUiModel(weatherDomainModel, fakeCity) } returns weatherUiModel

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        coVerify { weatherDataStoreRepository.saveData(weatherDomainModel) }

        val expectedWeatherModel = WeatherModel(
            city = fakeCity,
            iconUrl = fakeIconUrl,
            temperature = fakeTemperature,
            weatherDescription = fakeDescription,
            dailyForecasts = listOf()
        )
        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Success(expectedWeatherModel)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `When fetch weather returns failure on fetching location`() = runBlocking {
        coEvery { ipRepository.fetchLocation() } throws Exception(fakeError)

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `When fetch weather returns failure on fetching weather data`() = runBlocking {
        val ipDomainModel = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainModel

        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } throws Exception(fakeError)

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `When fetch weather returns failure on saving data`() = runBlocking {
        val ipDomainModel = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainModel

        val weatherDomainModel = mockk<WeatherDomainModel>()
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainModel

        coEvery { weatherDataStoreRepository.saveData(weatherDomainModel) } throws IOException(fakeError)

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `When fetch weather returns failure on mapping domain model to UI model`() = runBlocking {
        val ipDomainModel = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainModel

        val weatherDomainModel = mockk<WeatherDomainModel>(relaxed = true) {
            every { latitude } returns 33.8743F
            every { longitude } returns -84.4653F
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainModel

        coEvery { weatherDataStoreRepository.saveData(weatherDomainModel) } just Runs

        every { weatherDomainModelMapper.mapToUiModel(weatherDomainModel, fakeCity) } throws IOException(fakeError)

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        coVerify { weatherDataStoreRepository.saveData(weatherDomainModel) }

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }
}
