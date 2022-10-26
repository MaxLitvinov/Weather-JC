package com.jc.weather.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jc.weather.R
import com.jc.weather.foundation.resources.WeatherJCTheme

private const val DURATION = 2000

// TODO: Remove splash screen page
@Composable
fun SplashScreenPage() {
    var visible by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.home_page_blue),
                        colorResource(R.color.home_page_purple)
                    ),
                    startY = 0F,
                    endY = getDisplayHeight()
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val originSize = 108.dp
        val imageContainerSize = originSize * 2.68F
        val topPadding = originSize / 4.5F
        AnimatedVisibility(
            visible = visible,
            modifier = Modifier
                .size(imageContainerSize, imageContainerSize)
                .background(color = Color.Green.copy(alpha = 0.4F) ),
            enter = slideInHorizontally(
                animationSpec = tween(durationMillis = DURATION)
            ) { fullWidth ->
                // Offsets the content by 1/3 of its width to the left, and slide towards right
                // Overwrites the default animation with tween for this slide animation.
                -fullWidth / 3
            } + fadeIn(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = DURATION)
            ),
            exit = slideOutHorizontally(
                animationSpec = spring(stiffness = Spring.StiffnessHigh)
            ) {
                // Overwrites the ending position of the slide-out to 200 (pixels) to the right
                DURATION
            } + fadeOut(
                animationSpec = tween(durationMillis = DURATION)
            )
        ) {
            // Content that needs to appear/disappear goes here:
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
private fun getDisplayHeight(): Float = with(LocalDensity.current) {
    return LocalConfiguration.current.screenHeightDp.dp.toPx()
}

@Preview
@Composable
fun SplashScreenPreview() {
    WeatherJCTheme {
        SplashScreenPage()
    }
}
