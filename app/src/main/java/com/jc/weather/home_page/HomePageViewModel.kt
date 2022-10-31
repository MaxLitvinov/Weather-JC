package com.jc.weather.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.weather.home_page.core.HomePageInteractor
import com.jc.weather.home_page.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val interactor: HomePageInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = interactor.fetchWeather()
        }
    }

    fun retry() {
        _uiState.value = UiState.Loading
        fetchWeather()
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(val weatherModel: WeatherModel) : UiState()

        data class Failure(val message: String) : UiState()
    }
}
