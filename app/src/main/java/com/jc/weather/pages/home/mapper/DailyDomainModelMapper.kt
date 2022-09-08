package com.jc.weather.pages.home.mapper

import com.jc.weather.domain.model.DailyDomainModel
import com.jc.weather.pages.home.model.DayForecast
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyDomainModelMapper @Inject constructor() {

    companion object {

        private const val DAY_FORMAT = "dd.MM EEEE"
        private const val TIME_ZONE_UTC = "UTC"
    }

    fun mapToUiModel(domainModel: DailyDomainModel) = with(domainModel.temperature) {
        DayForecast(
            dayName = getDay(domainModel.time),
            morning = morning,
            day = day,
            evening = evening,
            night = night,
            min = min,
            max = max
        )
    }

    private fun getDay(date: Long): String {
        val simpleDateFormat = SimpleDateFormat(DAY_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(TIME_ZONE_UTC)
        }
        val milliseconds = TimeUnit.SECONDS.toMillis(date)
        return simpleDateFormat.format(Date(milliseconds))
    }
}