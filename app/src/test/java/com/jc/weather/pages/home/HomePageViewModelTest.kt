package com.jc.weather.pages.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jc.weather.pages.home.core.HomePageInteractor
import com.jc.weather.pages.home.model.DayForecast
import com.jc.weather.pages.home.model.WeatherModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomePageViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var interactor: HomePageInteractor
    private lateinit var viewModel: HomePageViewModel

    @Before
    fun setup() {
        interactor = mockk()
        viewModel = HomePageViewModel(interactor)
    }

    @Test
    fun `Successful fetch request`() = runBlocking {
        val weatherModel = WeatherModel(
            "Chernihiv",
            "https://openweathermap.org/img/wn/04d@2x.png",
            "11.5",
            "Overcast clouds",
            listOf(
                DayForecast("Today", 0.0, 11.52, 0.0, 10.16, 0.0, 0.0),
                DayForecast("Tomorrow", 0.0, 16.1, 0.0, 11.97, 0.0, 0.0),
                DayForecast("15.09 Thursday", 0.0, 17.9, 0.0, 12.89, 0.0, 0.0),
                DayForecast("16.09 Friday", 0.0, 17.68, 0.0, 16.12, 0.0, 0.0),
                DayForecast("17.09 Saturday", 0.0, 13.14, 0.0, 10.54, 0.0, 0.0),
                DayForecast("18.09 Sunday", 0.0, 13.34, 0.0, 13.15, 0.0, 0.0),
                DayForecast("19.09 Monday", 0.0, 10.93, 0.0, 10.92, 0.0, 0.0),
                DayForecast("20.09 Tuesday", 0.0, 14.94, 0.0, 11.3, 0.0, 0.0)
            )
        )
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.WeatherUiState.Success(weatherModel)

        Assert.assertEquals(HomePageViewModel.WeatherUiState.Loading, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.WeatherUiState.Success(weatherModel)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }

    @Test
    fun `Failure fetch request`() = runBlocking {
        val errorMessage = "Some error"
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.WeatherUiState.Failure(errorMessage)

        Assert.assertEquals(HomePageViewModel.WeatherUiState.Loading, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.WeatherUiState.Failure(errorMessage)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }
}
