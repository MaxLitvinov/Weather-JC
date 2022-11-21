package com.jc.weather.logger

import timber.log.Timber

class DebugLogger : Logger {

    override fun initTrees() {
        Timber.plant(Timber.DebugTree())
    }

    override fun report(message: String) {
        Timber.d(message)
    }

    override fun report(throwable: Throwable) {
        Timber.e(throwable)
    }
}
