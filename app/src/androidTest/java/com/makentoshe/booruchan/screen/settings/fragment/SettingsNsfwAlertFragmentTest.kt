package com.makentoshe.booruchan.screen.settings.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.settings.model.OnDialogResultListener
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsNsfwAlertFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instrumentation)
    private lateinit var fragment: SettingsNsfwAlertFragment

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val listener = OnDialogResultListener.create()
            val fragmentManager = activity.supportFragmentManager
            fragment = SettingsNsfwAlertFragment.show(fragmentManager, listener)
        }
    }

    @Test
    fun shouldContainContent_EN() {
        onView(withText(R.string.user_agreement)).check(matches(isDisplayed()))
        onView(withText(R.string.user_agreement_content)).check(matches(isDisplayed()))
        onView(withText(R.string.agree)).check(matches(isDisplayed()))
        onView(withText(R.string.disagree)).check(matches(isDisplayed()))
    }

}