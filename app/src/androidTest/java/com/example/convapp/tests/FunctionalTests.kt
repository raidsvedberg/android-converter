package com.example.convapp.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.convapp.base.BaseTest
import com.example.convapp.pages.MainPage
import com.example.convapp.utils.TestData
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import org.junit.Test
import org.junit.runner.RunWith

@Epic("Converter App")
@Feature("Functional Tests")
@RunWith(AndroidJUnit4::class)
class FunctionalTests : BaseTest() {

    private val page = MainPage()

    @Test
    fun tabsSwitchCategories() {
        page.clickTab(TestData.VOLUME_TAB)
        page.verifyTabsVisibleAfterScrolling()
    }

    @Test
    fun convert100MilesToKm() {
        page.clickTab(TestData.LENGTH_TAB)
            .selectConversion("Miles ↔ Kilometers")
        page.typeValue(TestData.MILES_INPUT)
            .verifyResult(TestData.MILES_TO_KM)
    }

    @Test
    fun swapConversionWorks() {
        page.clickTab(TestData.LENGTH_TAB)
            .selectConversion("Miles ↔ Kilometers")
        page.typeValue(TestData.MILES_INPUT)
            .clickSwap()
            .verifyResult(TestData.SWAP_RESULT)
    }

    @Test
    fun clearButtonClearsResult() {
        page.typeValue(TestData.MILES_INPUT)
            .clearInput()
            .verifyResult(TestData.EMPTY)
    }

    @Test
    fun negativeTemperatureWorks() {
        page.clickTab(TestData.VOLUME_TAB)
        page.clickTab(TestData.TEMPERATURE_TAB)
            .typeValue(TestData.NEG_TEMP)
            .verifyResult(TestData.NEG_TEMP_RESULT)
    }
}