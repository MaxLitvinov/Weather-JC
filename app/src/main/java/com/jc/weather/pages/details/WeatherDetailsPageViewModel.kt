package com.jc.weather.pages.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.weather.pages.details.model.CollapsedModel
import com.jc.weather.pages.details.model.ExpandedModel
import com.jc.weather.pages.details.model.WindModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsPageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    private val _expandedItemIdList = MutableStateFlow(listOf<Int>())
    val expandedItemIdList: StateFlow<List<Int>> get() = _expandedItemIdList

    fun fetchDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: Fake list
            val fakeList = listOf(
                CollapsedModel(0, false, "00:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(1, false, "01:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(2, false, "02:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(3, false, "03:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(4, false, "04:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(5, false, "05:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(6, false, "06:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(7, false, "07:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(8, false, "08:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(9, false, "09:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(10, false, "10:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(11, false, "11:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(12, false, "12:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(13, false, "13:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(14, false, "14:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(15, false, "15:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(16, false, "16:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(17, false, "17:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(18, false, "18:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(19, false, "19:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(20, false, "20:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(21, false, "21:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(22, false, "22:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
                CollapsedModel(23, false, "23:15", "https://openweathermap.org/img/wn/04d@2x.png", 84, "rain", 17F, ExpandedModel(18.0F, 1012, 7.33F, 0.26F, 100, 10000, 0.11F, WindModel(2.18F, 175, 4.27F), "light rain")),
            )
            _uiState.value = UiState.Loaded(fakeList)
        }
    }

    fun onItemClick(id: Int) {
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
