package com.jc.weather.logger.tree

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {

    companion object {

        private const val UNKNOWN_ERROR = "Unknown error"
    }

    override fun log(priority: Int, t: Throwable?) =
        doAfterCheck(priority) {
            if (t != null) {
                FirebaseCrashlytics.getInstance().recordException(t)
            } else {
                FirebaseCrashlytics.getInstance().log(UNKNOWN_ERROR)
            }
        }

    private fun doAfterCheck(priority: Int, action: () -> Unit) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            action()
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) =
        doAfterCheck(priority) {
            if (t != null) {
                FirebaseCrashlytics.getInstance().recordException(t)
            } else {
                FirebaseCrashlytics.getInstance().log(message)
            }
        }
}
