package com.jc.weather.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.weather.pages.home.model.DayForecast
import com.jc.weather.pages.home.model.WeatherUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    companion object {

        private const val UNKNOWN_ERROR = "Unknown error"
    }

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = WeatherUiState.Loaded(createFakeModel())
            } catch (ex: Exception) {
                _uiState.value = WeatherUiState.Error(ex.message ?: UNKNOWN_ERROR)
                ex.printStackTrace()
            }
        }
    }

    /**
     * Temp method for testing purpose.
     *
     * TODO: Remove this method before release
     */
    private fun createFakeModel() = WeatherUiModel(
        city = "Kharkiv",
        temperature = "20",
        weatherDescription = "Rainy",
        weekForecast = listOf(
            DayForecast("Monday", 18F, 7F),
            DayForecast("Tuesday", 21F, 13F),
            DayForecast("Wednesday", 19F, 12F),
            DayForecast("Thursday", 20F, 12F),
            DayForecast("Friday", 21F, 14F),
            DayForecast("Saturday", 22F, 14F),
            DayForecast("Sunday", 14F, 9F)
        )
    )

    sealed class WeatherUiState {

        object Empty : WeatherUiState()

        object Loading : WeatherUiState()

        data class Loaded(val data: WeatherUiModel) : WeatherUiState()

        data class Error(val message: String) : WeatherUiState()
    }
}