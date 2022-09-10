package com.jc.weather.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.weather.pages.home.core.HomePageInteractor
import com.jc.weather.pages.home.model.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val interactor: HomePageInteractor
) : ViewModel() {

    companion object {

        private const val CHERNIHIV_LAT: Double = 51.5007564
        private const val CHERNIHIV_LON: Double = 31.2944966
    }

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = interactor.fetchWeather(CHERNIHIV_LAT, CHERNIHIV_LON)
        }
    }

    sealed class WeatherUiState {

        object Loading : WeatherUiState()

        data class Success(val data: WeatherModel) : WeatherUiState()

        data class Failure(val message: String) : WeatherUiState()
    }
}