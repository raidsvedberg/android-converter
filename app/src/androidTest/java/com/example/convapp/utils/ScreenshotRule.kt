package com.example.convapp.utils

import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ScreenshotRule : TestWatcher() {

    override fun failed(e: Throwable?, description: Description) {
        try {
            val capture = Screenshot.capture()
            capture.name = "${description.methodName}_failed"
            capture.process()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}