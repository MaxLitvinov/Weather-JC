package com.jc.weather.logger.tree

import android.os.Environment
import android.util.Log
import com.jc.weather.time.TimestampProvider
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.util.Date

class FileReportingTree constructor(
    private val timestampProvider: TimestampProvider
) : Timber.Tree() {

    companion object {

        private const val PATH = "Weather-JC app logs"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.DEBUG) {
            return super.log(priority, tag, message, t)
        }

        // TODO: Check if the app has Write/Read storage permissions

        val fileName = createFilename()
        val logTimeStamp = createLogTimestamp()
        val file: File? = generateFile(fileName)

        // If file created or exists save logs
        when {
            file != null -> writeToFile(file, logTimeStamp, message)
            else -> Timber.e("File for logging doesn't exist")
        }
    }

    private fun createFilename(): String {
        val timestamp = timestampProvider.toFileReportNamePattern(Date())
        return "$timestamp.html"
    }

    private fun createLogTimestamp(): String = timestampProvider.toFileReportPattern(Date())

    private fun generateFile(fileName: String): File? {
        if (!isExternalStorageAvailable) {
            Timber.e("External storage is not available. Check 'Storage' permission.")
            return null
        }

        val root = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), PATH)
        var dirExists = true
        if (!root.exists()) {
            dirExists = root.mkdirs()
        }
        if (!dirExists) {
            Timber.e("Cannot create directories: $root. Check 'Storage' permission.")
        }

        return File(root, fileName)
    }

    private val isExternalStorageAvailable: Boolean =
        (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState())

    private fun writeToFile(
        file: File,
        time: String,
        message: String
    ) = try {
        val writer = FileWriter(file, true)
        writer.append("<p>")
            .append("<strong>$time</strong>")
            .append(" $message")
            .append("</p>")

        writer.flush()
        writer.close()
    } catch (e: Exception) {
        Timber.e("Write to file exception: $e")
    }
}
