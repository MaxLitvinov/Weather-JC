package com.jc.weather.pages.home.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jc.weather.R
import com.jc.weather.pages.home.model.DayForecast

@Composable
fun dailyForecasts(
    dailyForecasts: List<DayForecast>,
    onDayForecastClick: (DayForecast) -> Unit
) = getDayNightTemperatureMaxLengths(dailyForecasts).let { (dayTempMaxLength, nightTempMaxLength) ->
    dailyForecasts.map { dayForecast ->
        val datNightTemp = stringResource(
            R.string.home_page_day_night_temperature_values,
            getDayTemperature(dayForecast.day, dayTempMaxLength),
            getNightTemperature(dayForecast.night, nightTempMaxLength)
        )
        Spacer(Modifier.height(8.dp))
        DayTextButton(dayForecast.dayName, datNightTemp) {
            onDayForecastClick(dayForecast)
        }
    }
}

private fun getDayNightTemperatureMaxLengths(dailyForecasts: List<DayForecast>): Pair<Int, Int> {
    var dayTempMaxStringLength = 0
    var nightTempMaxStringLength = 0
    dailyForecasts.map { dayForecast ->
        val dayTempLength = dayForecast.day.toString().length
        dayTempMaxStringLength = Math.max(dayTempMaxStringLength, dayTempLength)

        val nightTempLength = dayForecast.night.toString().length
        nightTempMaxStringLength = Math.max(dayTempMaxStringLength, nightTempLength)
    }
    return Pair(dayTempMaxStringLength, nightTempMaxStringLength)
}

@Composable
private fun getDayTemperature(temperature: Double, maxStringLength: Int): String {
    val currentDayTempLength = temperature.toString().length
    var dayValue: String = stringResource(R.string.home_page_temperature_value, temperature)
    if (currentDayTempLength < maxStringLength) {
        val lengthDifference = maxStringLength - currentDayTempLength
        for (spaceCount in 0..lengthDifference) {
            dayValue += " "
        }
    }
    return dayValue
}

@Composable
private fun getNightTemperature(temperature: Double, maxStringLength: Int): String {
    val currentNightTempLength = temperature.toString().length
    var nightValue: String =
        stringResource(R.string.home_page_temperature_value, temperature)
    if (currentNightTempLength < maxStringLength) {
        val lengthDifference = maxStringLength - currentNightTempLength
        var spaces = ""
        for (spaceCount in 0..lengthDifference) {
            spaces += " "
        }
        nightValue = spaces + nightValue
    }
    return nightValue
}
