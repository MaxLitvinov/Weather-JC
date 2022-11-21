package com.jc.weather.home_page.mapper

import android.content.Context
import com.jc.weather.foundation.resources.R
import com.jc.weather.home_page.model.DayForecast
import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.time.TimestampProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timestampProvider: TimestampProvider,
    private val dailyDomainModelMapper: DailyDomainModelMapper
) {

    companion object {

        const val ONE_DAY_IN_SECONDS = 86_400
    }

    fun mapToUiModel(
        domainModel: WeatherDomainModel,
        city: String
    ) = with(domainModel) {
        WeatherModel(
            city = city,
            iconUrl = currentWeather.weatherDetails.iconUrl,
            temperature = currentWeather.temperature.toString(),
            weatherDescription = currentWeather.weatherDetails.detailedDescription
                .replaceFirstChar { it.titlecase() },
            dailyForecasts = getDailyForecastList(this)
        )
    }

    private fun getDailyForecastList(
        domainModel: WeatherDomainModel
    ): List<DayForecast> = with(domainModel) {
        val currentDayTime = currentWeather.currentTime + timezoneOffset // TODO: Move timezone offset mapping into domain model layer
        val dailyForecasts = dailyForecasts.map(dailyDomainModelMapper::mapToUiModel)
        return transformDayNames(currentDayTime, dailyForecasts)
    }

    private fun transformDayNames(
        currentTimeInSeconds: Long,
        source: List<DayForecast>
    ): List<DayForecast> =
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
