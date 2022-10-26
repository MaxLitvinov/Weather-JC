package com.jc.weather.hourly_forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.weather.hourly_forecast.model.CollapsedModel
import com.jc.weather.hourly_forecast.repository.HourlyForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyForecastListPageViewModel @Inject constructor(
    private val hourlyForecastRepository: HourlyForecastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    private val _expandedItemIdList = MutableStateFlow(listOf<Long>())
    val expandedItemIdList: StateFlow<List<Long>> get() = _expandedItemIdList

    fun fetchDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val hourlyForecastList = hourlyForecastRepository.fetchForecast()
            _uiState.value = UiState.Loaded(hourlyForecastList)
        }
    }

    fun onItemClick(id: Long) {
        _expandedItemIdList.value = _expandedItemIdList.value.toMutableList().also { list ->
            when (list.contains(id)) {
                true -> list.remove(id)
                false -> list.add(id)
            }
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Loaded(val list: List<CollapsedModel>) : UiState()
    }
}
