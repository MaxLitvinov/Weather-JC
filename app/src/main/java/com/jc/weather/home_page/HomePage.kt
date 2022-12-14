package com.jc.weather.home_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jc.weather.dialog.ProgressDialog
import com.jc.weather.foundation.resources.R
import com.jc.weather.home_page.ui.ErrorDialog
import com.jc.weather.home_page.ui.WeatherScreen
import com.jc.weather.view.RetryScreen

@Composable
fun HomePage(
    viewModel: HomePageViewModel,
    onDayForecastClick: (Long) -> Unit
) = Column(
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
        .verticalScroll(state = rememberScrollState()),
    verticalArrangement = Arrangement.Center
) {
    viewModel.fetchWeather()
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is HomePageViewModel.UiState.Loading -> {
            ProgressDialog()
        }
        is HomePageViewModel.UiState.Success -> {
            WeatherScreen(uiState.weatherModel, onDayForecastClick)
        }
        is HomePageViewModel.UiState.Failure -> {
            RetryScreen { viewModel.retry() }
            ErrorDialog(uiState.message)
        }
    }
}

@Composable
private fun getDisplayHeight(): Float = with(LocalDensity.current) {
    return LocalConfiguration.current.screenHeightDp.dp.toPx()
}
