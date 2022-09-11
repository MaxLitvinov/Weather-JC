package com.jc.weather.pages.home.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jc.weather.R
import com.jc.weather.pages.home.model.DayForecast
import com.jc.weather.pages.home.model.WeatherModel
import com.jc.weather.ui.theme.WeatherJCTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WeatherScreen(model: WeatherModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val textStyle = TextStyle(
            color = colorResource(R.color.white),
            shadow = Shadow(
                color = colorResource(R.color.white),
                offset = Offset(3F, 3F),
                blurRadius = 5F
            )
        )

        TextButton(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .padding(start = 4.dp, end = 4.dp),
            onClick = {
                // TODO: Stub. Navigate user to Google maps page
                Toast.makeText(context, "Next page doesn't exist yet", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = model.city,
                style = textStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
            )
        }
        Spacer(Modifier.height(8.dp))
        GlideImage(
            imageModel = model.iconUrl,
            previewPlaceholder = R.drawable.ic_launcher_foreground,
            modifier = Modifier.height(200.dp),
            contentDescription = model.weatherDescription,
            loading = {
                Box(modifier = Modifier.matchParentSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            failure = {
                Image(
                    contentDescription = model.weatherDescription,
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    modifier = Modifier.height(200.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            style = textStyle.copy(fontSize = 64.sp, fontWeight = FontWeight.Bold),
            text = stringResource(R.string.home_page_weather, model.temperature)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            style = textStyle,
            text = model.weatherDescription
        )
        Spacer(Modifier.height(24.dp))
        dailyForecasts(model.dailyForecasts)
    }
}

@Composable
private fun dailyForecasts(dailyForecasts: List<DayForecast>) =
    getDayNightTemperatureMaxLengths(dailyForecasts).let { (dayTempMaxLength, nightTempMaxLength) ->
        dailyForecasts.map { dayForecast ->
            val datNightTemp = stringResource(
                R.string.home_page_day_night_temperature_values,
                getDayTemperature(dayForecast.day, dayTempMaxLength),
                getNightTemperature(dayForecast.night, nightTempMaxLength)
            )
            DayText(dayForecast.dayName, datNightTemp)
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

@Composable
private fun DayText(dayName: String, dayNightTempValues: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = TextStyle(
            color = colorResource(R.color.white),
            shadow = Shadow(
                color = colorResource(R.color.white),
                offset = Offset(3F, 3F),
                blurRadius = 5F
            )
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.Start),
            fontSize = 16.sp,
            style = textStyle,
            text = dayName
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.End),
            fontSize = 16.sp,
            style = textStyle,
            text = dayNightTempValues
        )
    }
}

@Preview(name = "Weather screen preview", showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    val fakeModel = WeatherModel(
        city = "Kharkiv",
        iconUrl = "",
        temperature = "20",
        weatherDescription = "Rainy",
        dailyForecasts = listOf(
            DayForecast("09.09 September", 0.0, 15.83, 0.0, 10.67, 0.0, 0.0),
            DayForecast("10.09 September", 0.0, 7.61, 0.0, 9.52, 0.0, 0.0),
            DayForecast("11.09 September", 0.0, 9.14, 0.0, 10.8, 0.0, 0.0),
            DayForecast("12.09 September", 0.0, 14.91, 0.0, 13.3, 0.0, 0.0),
            DayForecast("13.09 September", 0.0, 11.5, 0.0, 9.53, 0.0, 0.0),
            DayForecast("14.09 September", 0.0, 10.99, 0.0, 10.41, 0.0, 0.0),
            DayForecast("15.09 September", 0.0, 19.24, 0.0, 12.59, 0.0, 0.0),
            DayForecast("16.09 September", 0.0, 14.89, 0.0, 14.28, 0.0, 0.0)
        )
    )
    WeatherJCTheme {
        WeatherScreen(model = fakeModel)
    }
}
