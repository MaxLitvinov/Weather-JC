package com.jc.weather.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object WeatherDetails : Screen("weather_details") {

        const val DAY_ID = "dayId"
    }
}
