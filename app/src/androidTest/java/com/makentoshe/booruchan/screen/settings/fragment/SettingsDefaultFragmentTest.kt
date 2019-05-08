package com.makentoshe.booruchan.screen.settings.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsDefaultFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private lateinit var fragment: SettingsDefaultFragment

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commitNow()
        }
    }

    @Test
    fun shouldContainNsfwSetting() {
        onView(withId(R.id.setting_nsfw)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isDisplayed()))
    }

}