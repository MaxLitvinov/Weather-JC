package com.jc.weather.pages.home.core

import com.jc.weather.pages.home.HomePageViewModel
import com.jc.weather.pages.home.mapper.WeatherDomainModelMapper
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherDomainModelMapper: WeatherDomainModelMapper
) {

    companion object {

        private const val UNKNOWN_ERROR = "Unknown error"
    }

    suspend fun fetchWeather(lat: Double, lon: Double): HomePageViewModel.WeatherUiState =
        try {
            val weatherDomainModel = weatherRepository.fetchWeather("$lat", "$lon")
            val weatherUiModel = weatherDomainModelMapper.mapToUiModel(weatherDomainModel)
            HomePageViewModel.WeatherUiState.Loaded(weatherUiModel)
        } catch (ex: Exception) {
            ex.printStackTrace()
            HomePageViewModel.WeatherUiState.Error(ex.message ?: UNKNOWN_ERROR)
        }
}