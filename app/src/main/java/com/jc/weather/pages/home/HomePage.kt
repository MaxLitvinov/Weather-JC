package com.jc.weather.pages.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jc.weather.R
import com.jc.weather.pages.home.model.DayForecast
import com.jc.weather.pages.home.model.WeatherUiModel
import com.jc.weather.ui.theme.WeatherJCTheme

@Composable
fun HomePage(viewModel: HomePageViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.home_page_blue),
                        colorResource(R.color.home_page_purple)
                    ),
                    startY = 0F,
                    endY = getDisplayHeight()
                )
            )
            .verticalScroll(state = rememberScrollState())
    ) {
        when (val uiState = viewModel.uiState.collectAsState().value) {
            is HomePageViewModel.WeatherUiState.Empty -> EmptyScreen()
            is HomePageViewModel.WeatherUiState.Loading -> ProgressScreen()
            is HomePageViewModel.WeatherUiState.Loaded -> WeatherScreen(uiState.data)
            is HomePageViewModel.WeatherUiState.Error -> ErrorDialog(uiState.message)
        }
    }
}

@Composable
private fun getDisplayHeight(): Float = with(LocalDensity.current) {
    return LocalConfiguration.current.screenHeightDp.dp.toPx()
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 32.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.White,
                    offset = Offset(5F, 10F),
                    blurRadius = 3F
                )
            ),
            text = stringResource(id = R.string.home_page_no_data)
        )
    }
}

@Composable
private fun ProgressScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun WeatherScreen(model: WeatherUiModel) = with(model) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textStyle = TextStyle(
            color = colorResource(R.color.white),
            shadow = Shadow(
                color = colorResource(R.color.white),
                offset = Offset(3F, 3F),
                blurRadius = 5F
            )
        )

        Spacer(Modifier.height(32.dp))
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .height(200.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(8.dp))
        Text(
            style = textStyle.copy(fontSize = 64.sp, fontWeight = FontWeight.Bold),
            text = stringResource(R.string.home_page_weather, model.temperature)
        )
        model.weatherDescription?.let { description ->
            Spacer(Modifier.height(8.dp))
            Text(
                style = textStyle,
                text = description
            )
        }
        Spacer(Modifier.height(24.dp))
        weekForecast(weekForecast)
    }
}

@Composable
private fun weekForecast(weekForecast: List<DayForecast>) =
    getDayNightTemperatureMaxLengths(weekForecast).let { (dayTempMaxLength, nightTempMaxLength) ->
        weekForecast.map { dayForecast ->
            val datNightTemp = stringResource(
                R.string.home_page_day_night_temperature_values,
                getDayTemperature(dayForecast.dayTemp, dayTempMaxLength),
                getNightTemperature(dayForecast.nightTemp, nightTempMaxLength)
            )
            DayText(dayForecast.dayName, datNightTemp)
        }
    }

private fun getDayNightTemperatureMaxLengths(weekForecast: List<DayForecast>): Pair<Int, Int> {
    var dayTempMaxStringLength = 0
    var nightTempMaxStringLength = 0
    weekForecast.map { dayForecast ->
        val dayTempLength = dayForecast.dayTemp.toString().length
        dayTempMaxStringLength = Math.max(dayTempMaxStringLength, dayTempLength)

        val nightTempLength = dayForecast.nightTemp.toString().length
        nightTempMaxStringLength = Math.max(dayTempMaxStringLength, nightTempLength)
    }
    return Pair(dayTempMaxStringLength, nightTempMaxStringLength)
}

@Composable
private fun getDayTemperature(temperature: Float, maxStringLength: Int): String {
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
private fun getNightTemperature(temperature: Float, maxStringLength: Int): String {
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

@Composable
private fun ErrorDialog(message: String) {
    val isDialogOpened = remember { mutableStateOf(true) }
    if (isDialogOpened.value) {
        AlertDialog(
            modifier = Modifier.padding(8.dp),
            onDismissRequest = {
                isDialogOpened.value = false
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.dialog_title_error_occurred),
                    textAlign = TextAlign.Center,
                )
            },
            text = {
                Text(fontSize = 14.sp, text = message)
            },
            confirmButton = {
                TextButton(
                    border = BorderStroke(0.5.dp, colorResource(R.color.black)),
                    modifier = Modifier.padding(8.dp),
                    onClick = { isDialogOpened.value = false }
                ) {
                    Text(
                        color = colorResource(R.color.black),
                        text = stringResource(id = R.string.dialog_ok)
                    )
                }
            }
        )
    }
}

@Preview("Home page preview", showBackground = true)
@Composable
fun HomePagePreview() {
    WeatherJCTheme {
        HomePage()
    }
}

@Preview(name = "Empty screen preview", showBackground = true)
@Composable
private fun EmptyScreenPreview() {
    WeatherJCTheme {
        EmptyScreen()
    }
}

@Preview(name = "Progress screen preview", showBackground = true)
@Composable
private fun ProgressScreenPreview() {
    WeatherJCTheme {
        ProgressScreen()
    }
}

@Preview(name = "Weather screen preview", showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    val fakeModel = WeatherUiModel(
        city = "Kharkiv",
        temperature = "20",
        weatherDescription = "Rainy",
        weekForecast = listOf(
            DayForecast("Monday", 18F, 7F),
            DayForecast("Tuesday", 21F, 13F),
            DayForecast("Wednesday", 19F, 12F),
            DayForecast("Thursday", 20F, 12F),
            DayForecast("Friday", 21F, 14F),
            DayForecast("Saturday", 22F, 14F),
            DayForecast("Sunday", 14F, 9F)
        )
    )
    WeatherJCTheme {
        WeatherScreen(model = fakeModel)
    }
}

@Preview(name = "Error dialog preview", showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    WeatherJCTheme {
        ErrorDialog("Something has happened")
    }
}