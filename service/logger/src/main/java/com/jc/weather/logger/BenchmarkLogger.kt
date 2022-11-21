package com.jc.weather.logger

import com.jc.weather.logger.tree.FileReportingTree
import com.jc.weather.time.TimestampProvider
import timber.log.Timber

class BenchmarkLogger(
    private val timestampProvider: TimestampProvider
) : Logger {

    override fun initTrees() {
        Timber.plant(FileReportingTree(timestampProvider))
    }

    override fun report(message: String) {
        Timber.i(message)
    }

    override fun report(throwable: Throwable) {
        Timber.i(throwable)
    }
}
