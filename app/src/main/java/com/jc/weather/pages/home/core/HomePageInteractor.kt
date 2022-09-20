package com.jc.weather.pages.home.core

import android.content.Context
import com.jc.weather.R
import com.jc.weather.di.TimestampProvider
import com.jc.weather.domain.ip_api.model.IpDomainModel
import com.jc.weather.pages.home.HomePageViewModel
import com.jc.weather.pages.home.mapper.WeatherDomainModelMapper
import com.jc.weather.pages.home.model.DayForecast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timestampProvider: TimestampProvider,
    private val weatherRepository: WeatherRepository,
    private val ipRepository: IpRepository,
    private val mapper: WeatherDomainModelMapper
) {

    companion object {

        private const val ONE_DAY_IN_SECONDS = 86_400
    }

    suspend fun fetchWeather(): HomePageViewModel.UiState =
        try {
            val locationModel = fetchLocation()
            val weatherDomainModel = weatherRepository.fetchWeather("${locationModel.lat}", "${locationModel.lon}")
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
