package com.example.convapp.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.containsString
import com.example.convapp.R
import com.example.convapp.utils.TestData
import androidx.test.espresso.Espresso.onData
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf

class MainPage {

    fun titleName(text: String): MainPage {
        onView(withId(R.id.title)).
        check(matches(withText(containsString(text))))
        return this
    }

    fun typeValue(value: String): MainPage {
        onView(withId(R.id.inputValue))
            .perform(replaceText(value), closeSoftKeyboard())
        return this
    }

    fun clickSwap(): MainPage {
        onView(withId(R.id.swapButton)).perform(click())
        return this
    }

    fun verifyResult(text: String): MainPage {
        Thread.sleep(500)

        onView(withId(R.id.resultText))
            .check(matches(withText(containsString(text))))
        return this
    }

    fun verifyInputVisible(): MainPage {
        onView(withId(R.id.inputValue))
            .check(matches(isDisplayed()))
        return this
    }

    fun verifySwapVisible(): MainPage {
        onView(withId(R.id.swapButton))
            .check(matches(isDisplayed()))
        return this
    }

    fun verifyTabsVisible(): MainPage {
        onView(withText(TestData.LENGTH_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.AREA_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.WEIGHT_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.VOLUME_TAB)).check(matches(isDisplayed()))
        return this
    }

    fun verifyTabsVisibleAfterScrolling(): MainPage {
        onView(withText(TestData.AREA_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.WEIGHT_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.VOLUME_TAB)).check(matches(isDisplayed()))
        onView(withText(TestData.TEMPERATURE_TAB)).check(matches(isDisplayed()))
        return this
    }

    fun clearInput(): MainPage {
        onView(withId(R.id.inputValue))
            .perform(clearText())
        return this
    }

    fun clickTab(tabName: String): MainPage {
        onView(withText(tabName)).perform(click())
        return this
    }

    fun selectConversion(name: String): MainPage {
        onView(withId(R.id.conversionSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(name)))
            .perform(click())

        return this
    }
}