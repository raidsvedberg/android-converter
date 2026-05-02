package com.example.convapp.tests

import com.example.convapp.base.BaseTest
import com.example.convapp.pages.MainPage
import com.example.convapp.utils.TestData
import org.junit.Test

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