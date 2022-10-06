package com.jc.weather.foundation.resources

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

val mainTextStyle = TextStyle(
    color = White,
    shadow = Shadow(
        color = White,
        offset = Offset(3F, 3F),
        blurRadius = 5F
    )
)
