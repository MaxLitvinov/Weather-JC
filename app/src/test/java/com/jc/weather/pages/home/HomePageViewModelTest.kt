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
                DayForecast("Today", 0.0F, 11.52F, 0.0F, 10.16F, 0.0F, 0.0F),
                DayForecast("Tomorrow", 0.0F, 16.1F, 0.0F, 11.97F, 0.0F, 0.0F),
                DayForecast("15.09 Thursday", 0.0F, 17.9F, 0.0F, 12.89F, 0.0F, 0.0F),
                DayForecast("16.09 Friday", 0.0F, 17.68F, 0.0F, 16.12F, 0.0F, 0.0F),
                DayForecast("17.09 Saturday", 0.0F, 13.14F, 0.0F, 10.54F, 0.0F, 0.0F),
                DayForecast("18.09 Sunday", 0.0F, 13.34F, 0.0F, 13.15F, 0.0F, 0.0F),
                DayForecast("19.09 Monday", 0.0F, 10.93F, 0.0F, 10.92F, 0.0F, 0.0F),
                DayForecast("20.09 Tuesday", 0.0F, 14.94F, 0.0F, 11.3F, 0.0F, 0.0F)
            )
        )
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.UiState.Success(weatherModel)

        Assert.assertEquals(HomePageViewModel.UiState.Loading, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.UiState.Success(weatherModel)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }

    @Test
    fun `Failure fetch request`() = runBlocking {
        val errorMessage = "Some error"
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.UiState.Failure(errorMessage)

        Assert.assertEquals(HomePageViewModel.UiState.Loading, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.UiState.Failure(errorMessage)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }
}
