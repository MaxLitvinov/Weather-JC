package com.jc.weather.pages.home.mapper

import com.jc.weather.di.TimestampProvider
import com.jc.weather.domain.open_weather_map.model.DailyDomainModel
import com.jc.weather.pages.home.model.DayForecast
import javax.inject.Inject

class DailyDomainModelMapper @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun mapToUiModel(domainModel: DailyDomainModel) = with(domainModel.temperature) {
        DayForecast(
            dayName = timestampProvider.toDayMonthAndDayName(domainModel.time),
            morning = morning,
            day = day,
            evening = evening,
            night = night,
            min = min,
            max = max
        )
    }
}
