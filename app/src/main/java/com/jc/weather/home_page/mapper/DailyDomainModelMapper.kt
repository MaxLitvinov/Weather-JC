package com.jc.weather.home_page.mapper

import com.jc.weather.home_page.model.DayForecast
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.time.TimestampProvider
import javax.inject.Inject

class DailyDomainModelMapper @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun mapToUiModel(domainModel: DailyDomainModel) = with(domainModel.temperature) {
        DayForecast(
            id = domainModel.generateId(),
            dayName = domainModel.generateDayName(),
            morning = morning,
            day = day,
            evening = evening,
            night = night,
            min = min,
            max = max
        )
    }

    private fun DailyDomainModel.generateId(): Long? =
        hourlyForecasts?.let { time }

    private fun DailyDomainModel.generateDayName(): String =
        timestampProvider.toDayMonthAndDayName(time)
}
