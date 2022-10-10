package com.jc.weather.home_page.core

import android.content.Context
import com.jc.weather.R
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
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

    suspend fun fetchWeather(): HomePageViewModel.UiState =
        try {
            val locationModel = ipRepository.fetchLocation()
            val weatherDomainModel = weatherForecastRepository.fetchWeather("${locationModel.lat}", "${locationModel.lon}")

            weatherDataStoreRepository.saveData(weatherDomainModel)

            val weatherUiModel = mapper.mapToUiModel(weatherDomainModel, locationModel.city)
            HomePageViewModel.UiState.Success(weatherUiModel)
        } catch (ex: Exception) {
            ex.printStackTrace()
            HomePageViewModel.UiState.Failure(ex.message ?: context.getString(R.string.something_went_wrong))
        }
}
