package com.jc.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jc.weather.ui.WeatherApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition {
            override fun shouldKeepOnScreen(): Boolean {
                return viewModel.shouldKeepSplashScreenVisible
            }
        })
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}
