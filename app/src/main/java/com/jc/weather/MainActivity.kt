package com.jc.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jc.weather.pages.home.HomePage
import com.jc.weather.ui.theme.WeatherJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherJCTheme {
                HomePage()
            }
        }
    }
}