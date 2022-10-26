package com.jc.weather.hourly_forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jc.weather.R
import com.jc.weather.dialog.ProgressDialog
import com.jc.weather.foundation.resources.WeatherJCTheme
import com.jc.weather.hourly_forecast.model.CollapsedModel
import com.jc.weather.hourly_forecast.ui.ExpandableWeatherDetails

@Composable
fun WeatherDetailsPage(
    viewModel: HourlyForecastListPageViewModel,
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
) {
    viewModel.fetchDetails()
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is HourlyForecastListPageViewModel.UiState.Loading -> ProgressDialog()
        is HourlyForecastListPageViewModel.UiState.Loaded -> {
            val list = uiState.list
            val expandedItemIdList = viewModel.expandedItemIdList.collectAsState()
            ExpandableDetailList(
                list = list,
                onExpandableItemClick = viewModel::onItemClick,
                isExpanded = expandedItemIdList.value::contains
            )
        }
    }
}

@Composable
private fun getDisplayHeight(): Float = with(LocalDensity.current) {
    return LocalConfiguration.current.screenHeightDp.dp.toPx()
}

@Composable
private fun ExpandableDetailList(
    list: List<CollapsedModel>,
    onExpandableItemClick: (Long) -> Unit,
    isExpanded: (Long) -> Boolean
) = LazyColumn(
    state = rememberLazyListState(),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(list) { details ->
        ExpandableWeatherDetails(
            model = details,
            onClick = { onExpandableItemClick(details.id) },
            isExpanded = isExpanded(details.id)
        )
    }
}

@Preview(name = "Weather details page preview", showBackground = false)
@Composable
private fun WeatherDetailsPagePreview() {
    WeatherJCTheme {
        // TODO: Figure out how to set ViewModel without any injections needed
        // WeatherDetailsPage(viewModel = Any)
    }
}
