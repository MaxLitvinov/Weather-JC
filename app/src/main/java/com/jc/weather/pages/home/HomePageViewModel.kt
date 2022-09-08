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
    private val homePageInteractor: HomePageInteractor
) : ViewModel() {

    companion object {

        private const val CHERNIHIV_LAT: Double = 51.5007564
        private const val CHERNIHIV_LON: Double = 31.2944966
    }

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = homePageInteractor.fetchWeather(CHERNIHIV_LAT, CHERNIHIV_LON)
        }
    }

    sealed class WeatherUiState {

        object Empty : WeatherUiState()

        object Loading : WeatherUiState()

        data class Loaded(val data: WeatherModel) : WeatherUiState()

        data class Error(val message: String) : WeatherUiState()
    }
}