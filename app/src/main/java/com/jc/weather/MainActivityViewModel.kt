package com.jc.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {

    companion object {

        const val FAKE_DATA_LOADING_DELAY = 1200L
    }

    var shouldKeepSplashScreenVisible = true

    init {
        fakeDataLoading()
    }

    // TODO: Imitate data loading
    private fun fakeDataLoading() =
        viewModelScope.launch {
            delay(FAKE_DATA_LOADING_DELAY)
            shouldKeepSplashScreenVisible = false
        }
}
