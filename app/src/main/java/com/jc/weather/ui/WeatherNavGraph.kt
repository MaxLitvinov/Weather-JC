package com.jc.weather.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jc.weather.home_page.HomePage
import com.jc.weather.home_page.HomePageViewModel
import com.jc.weather.hourly_forecast.HourlyForecastListPageViewModel
import com.jc.weather.hourly_forecast.WeatherDetailsPage
import com.jc.weather.splash_screen.SplashScreenPage

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    startDestination: String = WeatherAppNavigation.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(WeatherAppNavigation.SPLASH_SCREEN_ROUTE) {
            SplashScreenPage()
        }
        composable(WeatherAppNavigation.HOME_ROUTE) {
            val viewModel = hiltViewModel<HomePageViewModel>()
            HomePage(viewModel = viewModel) {
                navController.navigate(WeatherAppNavigation.WEATHER_DETAILS_ROUTE)
            }
        }
        composable(WeatherAppNavigation.WEATHER_DETAILS_ROUTE) {
            val viewModel = hiltViewModel<HourlyForecastListPageViewModel>()
            WeatherDetailsPage(viewModel = viewModel)
        }
    }
}
