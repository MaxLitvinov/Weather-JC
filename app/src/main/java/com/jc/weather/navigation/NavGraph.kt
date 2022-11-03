package com.jc.weather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jc.weather.home_page.HomePage
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.hourly_forecast.HourlyForecastListPageViewModel
import com.jc.weather.hourly_forecast.WeatherDetailsPage

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<HomePageViewModel>()
            HomePage(viewModel = viewModel) { dayId ->
                navController.navigate("${Screen.WeatherDetails.route}/$dayId")
            }
        }
        composable(
            route = "${Screen.WeatherDetails.route}/{${Screen.WeatherDetails.DAY_ID}}",
            arguments = listOf(
                navArgument(
                    name = Screen.WeatherDetails.DAY_ID,
                    builder = { /* Do nothing */ }
                )
            )
        ) { backStackEntry ->
            val dayId = backStackEntry.arguments?.getString(Screen.WeatherDetails.DAY_ID)
            val viewModel = hiltViewModel<HourlyForecastListPageViewModel>()
            WeatherDetailsPage(dayId = dayId, viewModel = viewModel)
        }
    }
}
