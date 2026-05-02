package com.example.convapp.base

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.convapp.MainActivity
import com.example.convapp.utils.ScreenshotRule
import org.junit.Rule

open class BaseTest {

    @get:Rule(order = 0)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 1)
    val screenshotRule = ScreenshotRule()
}