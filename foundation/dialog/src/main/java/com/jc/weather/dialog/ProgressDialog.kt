package com.jc.weather.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jc.weather.foundation.resources.WeatherJCTheme

@Composable
fun ProgressDialog() {
    Dialog(
        onDismissRequest = { /* Do nothing */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(name = "Progress screen preview", showBackground = true)
@Composable
private fun ProgressScreenPreview() {
    WeatherJCTheme {
        ProgressDialog()
    }
}
