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
