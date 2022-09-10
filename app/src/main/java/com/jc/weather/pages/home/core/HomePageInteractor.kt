package com.jc.weather.pages.home.core

import android.content.Context
import com.jc.weather.R
import com.jc.weather.pages.home.HomePageViewModel
import com.jc.weather.pages.home.mapper.WeatherDomainModelMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: WeatherRepository,
    private val mapper: WeatherDomainModelMapper
) {

    suspend fun fetchWeather(lat: Double, lon: Double): HomePageViewModel.WeatherUiState =
        try {
            val weatherDomainModel = repository.fetchWeather("$lat", "$lon")
            val weatherUiModel = mapper.mapToUiModel(weatherDomainModel)
            HomePageViewModel.WeatherUiState.Success(weatherUiModel)
        } catch (ex: Exception) {
            ex.printStackTrace()
            HomePageViewModel.WeatherUiState.Failure(
                ex.message ?: context.getString(R.string.something_went_wrong)
            )
        }
}