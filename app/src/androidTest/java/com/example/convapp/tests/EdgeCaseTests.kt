package com.example.convapp.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.convapp.base.BaseTest
import com.example.convapp.pages.MainPage
import com.example.convapp.utils.TestData
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EdgeCaseTests : BaseTest() {

    private val page = MainPage()

    @Test
    fun emptyInput() {
        page.typeValue(TestData.EMPTY)
            .verifyResult(TestData.EMPTY)
    }

    @Test
    fun decimalInputMiToKm() {
        page.clickTab(TestData.LENGTH_TAB)
            .selectConversion("Miles ↔ Kilometers")
            .typeValue(TestData.DECIMAL_INPUT)
            .verifyResult(TestData.DECIMAL_RESULT)
    }

    @Test
    fun largeNumbers() {
        page.typeValue(TestData.LARGE_INPUT)
            .verifyResult(TestData.LARGE_RESULT)
    }
}