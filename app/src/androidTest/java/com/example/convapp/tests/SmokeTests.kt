package com.example.convapp.tests

import com.example.convapp.base.BaseTest
import com.example.convapp.pages.MainPage
import com.example.convapp.utils.TestData
import org.junit.Test

class SmokeTests : BaseTest() {

    private val page = MainPage()

    @Test
    fun appLaunchesSuccessfully() {
        page.verifyTabsVisible()
        page.titleName(TestData.APP_TITLE)
    }

    @Test
    fun inputFieldVisible() {
        page.verifyInputVisible()
    }

    @Test
    fun swapButtonVisible() {
        page.verifySwapVisible()
    }
}

