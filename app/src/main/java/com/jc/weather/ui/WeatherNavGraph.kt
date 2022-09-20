package com.jc.weather.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jc.weather.pages.details.WeatherDetailsPage
import com.jc.weather.pages.details.WeatherDetailsPageViewModel
import com.jc.weather.pages.home.HomePage
import com.jc.weather.pages.home.HomePageViewModel

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    startDestination: String = WeatherAppNavigation.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(WeatherAppNavigation.HOME_ROUTE) { backStackEntry ->
            val viewModel = hiltViewModel<HomePageViewModel>()
            HomePage(viewModel = viewModel) {
                navController.navigate(WeatherAppNavigation.WEATHER_DETAILS_ROUTE)
            }
        }
        composable(WeatherAppNavigation.WEATHER_DETAILS_ROUTE) { backStackEntry ->
            val viewModel = hiltViewModel<WeatherDetailsPageViewModel>()
            WeatherDetailsPage(viewModel = viewModel)
        }
    }
}
