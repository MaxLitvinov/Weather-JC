package com.jc.weather.home_page.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jc.weather.R
import com.jc.weather.foundation.resources.WeatherJCTheme
import com.jc.weather.foundation.resources.mainTextStyle

@Composable
fun DayTextButton(
    dayName: String,
    dayNightTempValues: String,
    isEnabled: Boolean,
    onDayForecastClick: () -> Unit
) = Button(
    onClick = onDayForecastClick,
    modifier = Modifier
        .defaultMinSize(minHeight = 48.dp)
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp),
    enabled = isEnabled,
    elevation = null,
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.15F)),
    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White.copy(alpha = 0.05F)),
    contentPadding = PaddingValues(start = 4.dp, end = 4.dp)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DayNameText(dayName)
        DayNightTemperatureText(dayNightTempValues, isEnabled)
    }
}

@Composable
private fun RowScope.DayNameText(dayName: String) =
    Text(
        text = dayName,
        modifier = Modifier
            .weight(1F)
            .padding(end = 4.dp)
            .wrapContentWidth(Alignment.Start),
        fontSize = 16.sp,
        style = mainTextStyle
    )

@Composable
private fun RowScope.DayNightTemperatureText(
    dayNightTemperature: String,
    isChevronVisible: Boolean
) = Row(
    modifier = Modifier
        .weight(1F)
        .wrapContentWidth(Alignment.End),
    verticalAlignment = Alignment.CenterVertically
) {
    val textSize = 16.sp
    Text(
        text = dayNightTemperature,
        fontSize = textSize,
        style = mainTextStyle
    )

    val iconVisibility = if (isChevronVisible) 1F else 0F
    val iconHeight = with(LocalDensity.current) { textSize.toDp() }
    val paddingStart = 10.dp
    val iconWidth = iconHeight + paddingStart
    Image(
        painter = painterResource(R.drawable.ic_chevron_right),
        contentDescription = null,
        modifier = Modifier
            .alpha(iconVisibility)
            .size(iconWidth, iconHeight)
            .padding(start = paddingStart, top = 3.dp, bottom = 2.dp)
    )
}

@Preview(name = "Button is enabled", showBackground = false)
@Composable
private fun DayTextButtonEnabledPreview() = DayTextButtonPreview(isEnabled = true)

@Composable
private fun DayTextButtonPreview(isEnabled: Boolean) =
    WeatherJCTheme {
        DayTextButton(
            dayName = "12.09 Monday",
            dayNightTempValues = "12.01 °C / 9.07 °C",
            isEnabled = isEnabled
        ) {
            // Nothing to do
        }
    }

@Preview(name = "Button is disabled", showBackground = false)
@Composable
private fun DayTextButtonDisabledPreview() = DayTextButtonPreview(isEnabled = false)
