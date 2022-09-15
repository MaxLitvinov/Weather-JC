package com.jc.weather.di

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimestampProvider @Inject constructor(
    @UserLocale val locale: Locale
) {

    companion object {

        private const val DAY_MONTH_FORMAT = "dd.MM"
        private const val DAY_MONTH_DAY_NAME_FORMAT = "dd.MM EEEE"
        private const val TIME_ZONE_UTC = "UTC"
    }

    /**
     * Get date formatted as [DAY_MONTH_FORMAT].
     *
     * Example: "12.09".
     */
    fun toDayMonth(dateInSeconds: Long): String = mapTo(dateInSeconds, DAY_MONTH_FORMAT)

    private fun mapTo(dateInSeconds: Long, pattern: String): String {
        val milliseconds = TimeUnit.SECONDS.toMillis(dateInSeconds)
        return createDateFormat(pattern).format(Date(milliseconds))
    }

    private fun createDateFormat(pattern: String): DateFormat =
        SimpleDateFormat(pattern, locale).apply {
            timeZone = TimeZone.getTimeZone(TIME_ZONE_UTC)
        }

    /**
     * Get date formatted as [DAY_MONTH_DAY_NAME_FORMAT].
     *
     * Example: "12.09 Monday".
     */
    fun toDayMonthAndDayName(dateInSeconds: Long): String = mapTo(dateInSeconds, DAY_MONTH_DAY_NAME_FORMAT)
}
