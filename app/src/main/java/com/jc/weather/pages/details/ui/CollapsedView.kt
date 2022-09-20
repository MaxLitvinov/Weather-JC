package com.jc.weather.pages.details.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jc.weather.R
import com.jc.weather.pages.details.model.CollapsedModel
import com.jc.weather.pages.details.model.ExpandedModel
import com.jc.weather.pages.details.model.WindModel
import com.jc.weather.ui.theme.WeatherJCTheme
import com.jc.weather.ui.theme.mainTextStyle
import com.skydoves.landscapist.glide.GlideImage

private val iconSize = 32.dp

@Composable
fun ExpandableWeatherDetails(
    model: CollapsedModel,
    onClick: () -> Unit,
    isExpanded: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CollapsedView(
            model = model,
            onClick = onClick
        )
        ExpandableContent(
            model = model.details,
            isExpanded = isExpanded
        )
    }
}

@Composable
private fun CollapsedView(model: CollapsedModel, onClick: () -> Unit) = with(model) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth(),
        elevation = null,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.15F)),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White.copy(alpha = 0.2F)),
        contentPadding = PaddingValues(start = 4.dp, end = 4.dp)
    ) {
        Time(time = time)
        Description(
            iconUrl = iconUrl,
            humidity = humidity,
            description = generalDescription
        )
        Temperature(temperature = temperature)
        ChevronDownIcon()
    }
}

@Composable
private fun RowScope.Time(time: String) {
    Text(
        text = time,
        modifier = Modifier
            .weight(0.2F)
            .padding(start = 4.dp),
        textAlign = TextAlign.Start,
        style = mainTextStyle
    )
}

@Composable
private fun RowScope.Description(
    iconUrl: String,
    humidity: Int,
    description: String
) {
    Row(
        modifier = Modifier.weight(0.4F),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = iconUrl,
            modifier = Modifier.size(iconSize, iconSize),
            contentDescription = null,
            previewPlaceholder = R.drawable.ic_clouds
        )
        Text(
            text = stringResource(
                R.string.weather_details_page_humidity,
                humidity,
                description.replaceFirstChar { it.titlecase() }
            ),
            modifier = Modifier.padding(horizontal = 2.dp),
            style = mainTextStyle
        )
    }
}

@Composable
private fun RowScope.Temperature(temperature: Float) {
    Row(
        modifier = Modifier.weight(0.25F),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_temperature),
            contentDescription = null,
            modifier = Modifier.size(iconSize, iconSize)
        )
        Text(
            text = stringResource(R.string.weather_details_page_temperature, temperature),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 4.dp),
            style = mainTextStyle
        )
    }
}

@Composable
private fun RowScope.ChevronDownIcon() {
    Image(
        painter = painterResource(R.drawable.ic_chevron_down),
        contentDescription = null,
        modifier = Modifier
            .weight(0.1F)
            .size(iconSize, iconSize)
            .padding(horizontal = 12.dp)
    )
}

@Preview(name = "Collapsed preview", showBackground = false)
@Composable
private fun CollapsedPreview() {
    val fakeModel = CollapsedModel(
        1,
        false,
        "14:15",
        "iconUrl",
        84,
        "rain",
        17F,
        ExpandedModel(
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
    )
    WeatherJCTheme {
        ExpandableWeatherDetails(
            model = fakeModel,
            onClick = {},
            isExpanded = false
        )
    }
}

@Preview(name = "Expanded preview", showBackground = false)
@Composable
private fun ExpandedPreview() {
    val fakeModel = CollapsedModel(
        1,
        false,
        "14:15",
        "iconUrl",
        84,
        "rain",
        17F,
        ExpandedModel(
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
    )
    WeatherJCTheme {
        ExpandableWeatherDetails(
            model = fakeModel,
            onClick = {},
            isExpanded = true
        )
    }
}
