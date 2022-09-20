package com.jc.weather.pages.home.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun WeatherScreen(
    model: WeatherModel,
    onDayForecastClick: (DayForecast) -> Unit
) {
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

        CityButton(model, textStyle)
        Spacer(Modifier.height(8.dp))
        Image(model)
        Spacer(Modifier.height(8.dp))
        Temperature(model, textStyle)
        Spacer(Modifier.height(8.dp))
        Description(model, textStyle)
        Spacer(Modifier.height(24.dp))
        dailyForecasts(model.dailyForecasts) { dayForecast ->
            onDayForecastClick(dayForecast)
        }
    }
}

@Composable
private fun CityButton(model: WeatherModel, textStyle: TextStyle) {
    val context = LocalContext.current
    TextButton(
        onClick = {
            // TODO: Stub. Navigate user to Google maps page
            Toast.makeText(context, "Next page doesn't exist yet", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .padding(start = 4.dp, end = 4.dp)
    ) {
        Text(
            text = model.city,
            style = textStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
private fun Image(model: WeatherModel) {
    GlideImage(
        imageModel = model.iconUrl,
        modifier = Modifier.height(200.dp),
        contentDescription = model.weatherDescription,
        previewPlaceholder = R.drawable.ic_launcher_foreground,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        failure = {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = model.weatherDescription,
                modifier = Modifier.height(200.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        }
    )
}

@Composable
private fun Temperature(model: WeatherModel, textStyle: TextStyle) {
    Text(
        style = textStyle.copy(fontSize = 64.sp, fontWeight = FontWeight.Bold),
        text = stringResource(R.string.home_page_weather, model.temperature)
    )
}

@Composable
private fun Description(model: WeatherModel, textStyle: TextStyle) {
    Text(
        style = textStyle,
        text = model.weatherDescription
    )
}

@Preview(name = "Weather screen preview", showBackground = false)
@Composable
private fun WeatherScreenPreview() {
    val fakeModel = WeatherModel(
        city = "Kharkiv",
        iconUrl = "",
        temperature = "20.07",
        weatherDescription = "Rainy",
        dailyForecasts = listOf(
            DayForecast("Today", 0.0, 15.83, 0.0, 10.67, 0.0, 0.0),
            DayForecast("Tomorrow", 0.0, 7.61, 0.0, 9.52, 0.0, 0.0),
            DayForecast("24.09 Saturday", 0.0, 9.14, 0.0, 10.8, 0.0, 0.0),
            DayForecast("25.09 Sunday", 0.0, 14.91, 0.0, 13.3, 0.0, 0.0),
            DayForecast("26.09 Monday", 0.0, 11.5, 0.0, 9.53, 0.0, 0.0),
            DayForecast("27.09 Tuesday", 0.0, 10.99, 0.0, 10.41, 0.0, 0.0),
            DayForecast("28.09 Wednesday", 0.0, 19.24, 0.0, 12.59, 0.0, 0.0),
            DayForecast("29.09 Thursday", 0.0, 14.89, 0.0, 14.28, 0.0, 0.0)
        )
    )
    WeatherJCTheme {
        WeatherScreen(model = fakeModel) {
            // Nothing to do
        }
    }
}
