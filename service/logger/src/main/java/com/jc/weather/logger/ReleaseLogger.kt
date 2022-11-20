package com.jc.weather.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jc.weather.logger.tree.CrashReportingTree
import timber.log.Timber

class ReleaseLogger : Logger {

    override fun initTrees() {
        Timber.plant(CrashReportingTree())
    }

    override fun report(message: String) =
        FirebaseCrashlytics.getInstance().log(message)

    override fun report(throwable: Throwable) =
        FirebaseCrashlytics.getInstance().recordException(throwable)
}
