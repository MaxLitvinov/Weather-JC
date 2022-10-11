package com.jc.weather.home_page.core

import android.content.Context
import com.jc.weather.R
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.ip_api.domain.model.IpDomainResult
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.open_weather_map.domain.model.WeatherDomainResult
import com.jc.weather.open_weather_map.domain.repository.WeatherForecastRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherForecastRepository: WeatherForecastRepository,
    private val ipRepository: IpRepository,
    private val weatherDataStoreRepository: WeatherDataStoreRepository,
    private val mapper: WeatherDomainModelMapper
) {

    suspend fun fetchWeather(): HomePageViewModel.UiState = handleLocation()

    private suspend fun handleLocation(): HomePageViewModel.UiState =
        when (val ipDomainResult = ipRepository.fetchLocation()) {
            is IpDomainResult.Success -> handleWeather(ipDomainResult)
            is IpDomainResult.Failure -> failure(ipDomainResult.error)
        }

    private fun failure(errorMessage: String?) = HomePageViewModel.UiState.Failure(
        errorMessage ?: context.getString(R.string.something_went_wrong)
    )

    private suspend fun handleWeather(
        ipDomainResult: IpDomainResult.Success
    ): HomePageViewModel.UiState = with(ipDomainResult) {
        when (val weatherDomainResult = weatherForecastRepository.fetchWeather("$lat", "$lon")) {
            is WeatherDomainResult.Success -> {
                val weatherDomainModel = weatherDomainResult.model
                weatherDataStoreRepository.saveData(weatherDomainModel)

                val weatherUiModel = mapper.mapToUiModel(weatherDomainModel, city)
                HomePageViewModel.UiState.Success(weatherUiModel)
            }
            is WeatherDomainResult.Failure -> failure(weatherDomainResult.error)
        }
    }
}
