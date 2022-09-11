package com.jc.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jc.weather.pages.home.HomePage
import com.jc.weather.pages.home.HomePageViewModel
import com.jc.weather.ui.theme.WeatherJCTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var homePageViewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherJCTheme {
                HomePage(homePageViewModel)
            }
        }
    }
}
