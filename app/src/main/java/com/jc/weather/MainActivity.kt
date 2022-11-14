package com.jc.weather

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.jc.weather.ui.WeatherApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {

        private const val SPLASH_SCREEN_ANIMATION_DURATION = 2200L
    }

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAGGGG", "MainActivity.onCreate()")
        installSplashScreen().apply {
            /* TODO: Keep splash screen displayed?
            setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition {
                override fun shouldKeepOnScreen(): Boolean {
                    return viewModel.shouldKeepSplashScreenVisible
                }
            })*/
            setOnExitAnimationListener { splashScreenViewProvider ->
                initAnimation(splashScreenViewProvider) {
                    setContent {
                        WeatherApp()
                    }
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    private fun initAnimation(
        provider: SplashScreenViewProvider,
        onAnimationEnd: () -> Unit
    ) = with(provider) {
        val rotation = rotationAnimation(iconView)
        val moveLeft = moveLeftAnimation(view, iconView)
        val fadeOut = fadeOutAnimation(iconView)

        AnimatorSet().apply {
            play(rotation).with(moveLeft)
            play(fadeOut)
            doOnEnd {
                remove()
                onAnimationEnd()
            }
            start()
        }
    }

    private fun rotationAnimation(targetView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            targetView,
            View.ROTATION,
            0F,
            -3600F
        ).apply {
            interpolator = AnticipateInterpolator()
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }

    private fun moveLeftAnimation(view: View, iconView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_X,
            0F,
            -(view.width.toFloat() + iconView.width.toFloat() / 2)
        ).apply {
            interpolator = AnticipateInterpolator()
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }

    private fun fadeOutAnimation(targetView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            targetView,
            View.ALPHA,
            1F,
            1F,
            1F,
            0F
        ).apply {
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }
}
