package com.jc.weather.pages.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jc.weather.foundation.resources.R
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.home_page.core.HomePageInteractor
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.ip_api.domain.model.IpDomainResult
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.open_weather_map.domain.model.WeatherDomainResult
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

private const val fakeLatitude = 33.8743
private const val fakeLongitude = -84.4653
private const val fakeCity = "Kiev"
private const val fakeIconUrl = "iconUrl"
private const val fakeTemperature = "9.39"
private const val fakeDescription = "Overcast clouds"
private const val fakeError = "Error"
private const val fakeErrorFromContext = "Something went wrong.\nPlease, check your internet connection or try again later."

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

        every { context.getString(R.string.something_went_wrong) } returns fakeErrorFromContext
    }

    @Test
    fun `Check fetch weather is successful`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val weatherDomainResult = mockk<WeatherDomainResult.Success>(relaxed = true) {
            every { model } returns mockk(relaxed = true) {
                every { latitude } returns 33.8743F
                every { longitude } returns -84.4653F
            }
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainResult

        coEvery { weatherDataStoreRepository.saveData(weatherDomainResult.model) } just Runs

        val weatherUiModel = mockk<WeatherModel>(relaxed = true) {
            every { city } returns fakeCity
            every { iconUrl } returns fakeIconUrl
            every { temperature } returns fakeTemperature
            every { weatherDescription } returns fakeDescription
            every { dailyForecasts } returns listOf()
        }
        every { weatherDomainModelMapper.mapToUiModel(weatherDomainResult.model, fakeCity) } returns weatherUiModel

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        coVerify { weatherDataStoreRepository.saveData(weatherDomainResult.model) }

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
    fun `Check fetch location fails`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Failure>(relaxed = true) {
            every { error } returns null
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeErrorFromContext)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Check fetch location fails with error message`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Failure>(relaxed = true) {
            every { error } returns fakeError
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Check fetch weather fails`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val weatherDomainResult = mockk<WeatherDomainResult.Failure>(relaxed = true) {
            every { error } returns null
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainResult

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeErrorFromContext)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Check fetch weather fails with error message`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val weatherDomainResult = mockk<WeatherDomainResult.Failure>(relaxed = true) {
            every { error } returns fakeError
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainResult

        val actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        val expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Check retry fetching weather`() = runBlocking {
        val ipDomainResult = mockk<IpDomainResult.Success>(relaxed = true) {
            every { lat } returns fakeLatitude
            every { lon } returns fakeLongitude
            every { city } returns fakeCity
        }
        coEvery { ipRepository.fetchLocation() } returns ipDomainResult

        val weatherDomainResultFailure = mockk<WeatherDomainResult.Failure>(relaxed = true) {
            every { error } returns fakeError
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainResultFailure

        var actualUiState: HomePageViewModel.UiState = interactor.fetchWeather()

        var expectedUiState: HomePageViewModel.UiState = HomePageViewModel.UiState.Failure(fakeError)

        assertEquals(expectedUiState, actualUiState)

        val weatherDomainResultSuccess = mockk<WeatherDomainResult.Success>(relaxed = true) {
            every { model } returns mockk(relaxed = true) {
                every { latitude } returns 33.8743F
                every { longitude } returns -84.4653F
            }
        }
        coEvery { weatherForecastRepository.fetchWeather("$fakeLatitude", "$fakeLongitude") } returns weatherDomainResultSuccess

        coEvery { weatherDataStoreRepository.saveData(weatherDomainResultSuccess.model) } just Runs

        val weatherUiModel = mockk<WeatherModel>(relaxed = true) {
            every { city } returns fakeCity
            every { iconUrl } returns fakeIconUrl
            every { temperature } returns fakeTemperature
            every { weatherDescription } returns fakeDescription
            every { dailyForecasts } returns listOf()
        }
        every { weatherDomainModelMapper.mapToUiModel(weatherDomainResultSuccess.model, fakeCity) } returns weatherUiModel

        actualUiState = interactor.fetchWeather()

        coVerify { weatherDataStoreRepository.saveData(weatherDomainResultSuccess.model) }

        val expectedWeatherModel = WeatherModel(
            city = fakeCity,
            iconUrl = fakeIconUrl,
            temperature = fakeTemperature,
            weatherDescription = fakeDescription,
            dailyForecasts = listOf()
        )
        expectedUiState = HomePageViewModel.UiState.Success(expectedWeatherModel)

        assertEquals(expectedUiState, actualUiState)
    }
}
