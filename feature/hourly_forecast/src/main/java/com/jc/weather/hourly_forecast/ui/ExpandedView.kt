package com.jc.weather.hourly_forecast.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jc.weather.R
import com.jc.weather.foundation.resources.WeatherJCTheme
import com.jc.weather.foundation.resources.mainTextStyle
import com.jc.weather.hourly_forecast.model.ExpandedModel
import com.jc.weather.hourly_forecast.model.WindModel

private val iconSize = 32.dp

private const val FadeInAnimation = 300
private const val ExpandedAnimation = 300

private const val FadeOutAnimation = 300
private const val CollapsedAnimation = 300

@Composable
fun ExpandableContent(model: ExpandedModel, isExpanded: Boolean = true) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FadeInAnimation,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(ExpandedAnimation))
    }

    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FadeOutAnimation,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(CollapsedAnimation))
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Description(model.detailedDescription, model.rain)
            RowView {
                Clouds(model.clouds)
                Visibility(model.visibility)
            }
            RowView {
                DewPoint(model.dewPoint)
                UvIndex(model.uvIndex)
            }
            RowView {
                Pressure(model.pressure)
                FeelsLike(model.feelsLike)
                Wind(model.wind)
            }
        }
    }
}

@Composable
private fun Description(description: String, rain: Float?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
            .background(color = colorResource(R.color.weather_details_page_expanded_item_background))
            .border(
                width = 0.5.dp,
                color = colorResource(R.color.weather_details_page_expanded_item_border)
            ),
        contentAlignment = Alignment.Center
    ) {
        val text = when (rain != null && rain != 0.0F) {
            true -> stringResource(R.string.weather_details_page_description_with_rain, description, rain)
            false -> description
        }
        Text(
            text = text,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
            style = mainTextStyle
        )
    }
}

@Composable
private fun RowView(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
private fun RowScope.Clouds(cloudsPercentage: Int) {
    IconTextBox(
        R.drawable.ic_clouds,
        stringResource(R.string.weather_details_page_clouds_percentage, cloudsPercentage)
    )
}

@Composable
private fun RowScope.IconTextBox(@DrawableRes icon: Int, text: String) {
    BoxView {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(iconSize, iconSize)
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = 6.dp),
                style = mainTextStyle
            )
        }
    }
}

@Composable
private fun RowScope.BoxView(content: @Composable BoxScope.() -> Unit) = Box(
    modifier = Modifier
        .weight(0.5F)
        .fillMaxWidth()
        .padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
        .background(color = colorResource(R.color.weather_details_page_expanded_item_background))
        .border(
            width = 0.5.dp,
            color = colorResource(R.color.weather_details_page_expanded_item_border)
        ),
    contentAlignment = Alignment.Center,
    content = content
)

@Composable
private fun RowScope.Visibility(visibility: Int) {
    IconTextBox(
        R.drawable.ic_visibility,
        stringResource(R.string.weather_details_page_visibility, visibility)
    )
}

@Composable
private fun RowScope.DewPoint(dewPoint: Float) {
    IconTextBox(
        R.drawable.ic_dew_point,
        stringResource(R.string.weather_details_page_dew_point, dewPoint)
    )
}

@Composable
private fun RowScope.UvIndex(uvIndex: Float) {
    IconTextBox(
        R.drawable.ic_uv_index,
        stringResource(R.string.weather_details_page_uv_index, uvIndex)
    )
}

@Composable
private fun RowScope.Pressure(pressure: Int) {
    IconTextBox(
        R.drawable.ic_pressure,
        stringResource(R.string.weather_details_page_pressure, pressure)
    )
}

@Composable
private fun RowScope.FeelsLike(feelsLike: Float) {
    IconTextBox(
        R.drawable.ic_feels_like,
        stringResource(R.string.weather_details_page_feels_like, feelsLike)
    )
}

@Composable
private fun RowScope.Wind(model: WindModel) {
    val speed = stringResource(R.string.weather_details_page_speed, model.speed)
    val deg = stringResource(R.string.weather_details_page_deg, model.deg)
    val gust = stringResource(R.string.weather_details_page_gust, model.gust)
    val text = "$speed\n$deg\n$gust"
    IconTextBox(R.drawable.ic_wind, text)
}

@Preview(name = "Expanded content preview", showBackground = false)
@Composable
private fun ExpandedContentPreview() {
    val fakeModel = ExpandedModel(
        18.0F,
        1012,
        7.33F,
        0.26F,
        100,
        10000,
        0.11F,
        WindModel(
            2.18F,
            175,
            4.27F
        ),
        "light rain"
    )
    WeatherJCTheme {
        ExpandableContent(fakeModel, false)
    }
}
