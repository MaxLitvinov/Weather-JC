package com.jc.weather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jc.weather.foundation.resources.WeatherJCTheme

@Composable
fun WeatherApp() {
    WeatherJCTheme {
        val navController = rememberNavController()
        WeatherNavGraph(
            navController = navController
        )
    }
}
