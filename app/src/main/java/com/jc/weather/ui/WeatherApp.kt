package com.jc.weather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jc.weather.foundation.resources.WeatherJCTheme
import com.jc.weather.navigation.WeatherNavGraph

@Composable
fun WeatherApp() {
    WeatherJCTheme {
        val navController = rememberNavController()
        WeatherNavGraph(
            navController = navController
        )
    }
}
