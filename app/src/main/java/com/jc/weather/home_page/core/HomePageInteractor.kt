package com.jc.weather.home_page.core

import android.content.Context
import com.jc.weather.R
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.home_page.model.DayForecast
import com.jc.weather.ip_api.domain.model.IpDomainModel
import com.jc.weather.ip_api.domain.repository.IpRepository
import com.jc.weather.open_weather_map.data.data_store.WeatherDataStoreRepository
import com.jc.weather.open_weather_map.domain.repository.WeatherForecastRepository
import com.jc.weather.time.TimestampProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timestampProvider: TimestampProvider,
    private val weatherForecastRepository: WeatherForecastRepository,
    private val ipRepository: IpRepository,
    private val weatherDataStoreRepository: WeatherDataStoreRepository,
    private val mapper: WeatherDomainModelMapper
) {

    companion object {

        private const val ONE_DAY_IN_SECONDS = 86_400
    }

    suspend fun fetchWeather(): HomePageViewModel.UiState =
        try {
            val locationModel = fetchLocation()
            val weatherDomainModel = weatherForecastRepository.fetchWeather("${locationModel.lat}", "${locationModel.lon}")

            weatherDataStoreRepository.saveData(weatherDomainModel)

            val weatherUiModel = mapper.mapToUiModel(weatherDomainModel).apply {
                city = locationModel.city

                val currentDayTime = weatherDomainModel.currentWeather.currentTime + weatherDomainModel.timezoneOffset
                dailyForecasts = transformDayNames(currentDayTime, dailyForecasts)
            }
            HomePageViewModel.UiState.Success(weatherUiModel)
        } catch (ex: Exception) {
            ex.printStackTrace()
            HomePageViewModel.UiState.Failure(
                ex.message ?: context.getString(R.string.something_went_wrong)
            )
        }

    private suspend fun fetchLocation(): IpDomainModel = ipRepository.fetchLocation()

    private fun transformDayNames(currentTimeInSeconds: Long, source: List<DayForecast>): List<DayForecast> =
        source.map { dayForecast ->
            when {
                isToday(currentTimeInSeconds, dayForecast.dayName) ->
                    dayForecast.copy(dayName = context.getString(R.string.home_page_today))
                isTomorrow(currentTimeInSeconds, dayForecast.dayName) ->
                    dayForecast.copy(dayName = context.getString(R.string.home_page_tomorrow))
                else -> dayForecast
            }
        }

    private fun isToday(currentTime: Long, dayNameToCompare: String): Boolean {
        val currentDayName = timestampProvider.toDayMonthAndDayName(currentTime)
        return currentDayName == dayNameToCompare
    }

    private fun isTomorrow(currentTimeInSeconds: Long, dayNameToCompare: String): Boolean {
        val tomorrowTime = currentTimeInSeconds + ONE_DAY_IN_SECONDS
        val tomorrowDayName = timestampProvider.toDayMonthAndDayName(tomorrowTime)
        return tomorrowDayName == dayNameToCompare
    }
}
