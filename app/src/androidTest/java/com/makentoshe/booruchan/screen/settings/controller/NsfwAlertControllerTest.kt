package com.makentoshe.booruchan.screen.settings.controller

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.makentoshe.booruchan.TestActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NsfwAlertControllerTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instrumentation)

    private lateinit var activity: TestActivity
    private lateinit var controller: NsfwAlertController

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            controller = NsfwAlertController(activity.supportFragmentManager)
        }
    }

    @Test(timeout = 5000)
    fun shouldReturnTrueOnPositiveButtonClick() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        //click to the positive button
        onView(withId(android.R.id.button1)).perform(click())
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(true, result)
    }

    @Test(timeout = 5000)
    fun shouldReturnFalseOnNegativeButtonClick() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        //click to the negative button
        onView(withId(android.R.id.button2)).perform(click())
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(false, result)
    }

    @Test(timeout = 5000)
    fun shouldReturnFalseOnCancelOrOnDismiss() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        device.pressBack()
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(false, result)
    }

    @Test(timeout = 5000)
    fun shouldReturnTrueOnPositiveButtonClickAfterRotation() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        //rotate device
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //click to the positive button
        onView(withId(android.R.id.button1)).perform(click())
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(true, result)
    }

    @Test(timeout = 5000)
    fun shouldReturnFalseOnNegativeButtonClickAfterRotation() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        //rotate device
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //click to the negative button
        onView(withId(android.R.id.button2)).perform(click())
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(false, result)
    }

    @Test(timeout = 5000)
    fun shouldReturnFalseOnCancelOrOnDismissAfterRotation() {
        var result: Boolean? = null
        controller.showAlert { result = it }
        //rotate device
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        device.pressBack()
        //wait while click button will be performed and lambda executed
        while (result == null) Thread.yield()

        assertEquals(false, result)
    }
}