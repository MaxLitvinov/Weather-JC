package com.jc.weather.home_page.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jc.weather.R
import com.jc.weather.foundation.resources.WeatherJCTheme

@Composable
fun ErrorDialog(message: String) {
    val isDialogOpened = remember { mutableStateOf(true) }
    if (isDialogOpened.value) {
        AlertDialog(
            modifier = Modifier.padding(8.dp),
            onDismissRequest = {
                isDialogOpened.value = false
            },
            title = {
                Title()
            },
            text = {
                Description(message)
            },
            confirmButton = {
                OkButton { isDialogOpened.value = false }
            }
        )
    }
}

@Composable
private fun Title() = Text(
    modifier = Modifier.fillMaxSize(),
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    text = stringResource(id = R.string.dialog_title_error_occurred),
    textAlign = TextAlign.Center,
)

@Composable
private fun Description(message: String) = Text(fontSize = 14.sp, text = message)

@Composable
private fun OkButton(onClick: () -> Unit) = TextButton(
    border = BorderStroke(0.5.dp, colorResource(R.color.black)),
    modifier = Modifier.padding(8.dp),
    onClick = onClick
) {
    Text(
        color = colorResource(R.color.black),
        text = stringResource(id = R.string.dialog_ok)
    )
}

@Preview(name = "Error dialog preview", showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    WeatherJCTheme {
        ErrorDialog("Something has happened")
    }
}
