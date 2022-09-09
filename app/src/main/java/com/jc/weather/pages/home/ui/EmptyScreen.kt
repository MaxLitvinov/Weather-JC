package com.jc.weather.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jc.weather.R
import com.jc.weather.ui.theme.WeatherJCTheme

@Composable
fun EmptyScreen() {
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

@Preview(name = "Empty screen preview", showBackground = true)
@Composable
private fun EmptyScreenPreview() {
    WeatherJCTheme {
        EmptyScreen()
    }
}