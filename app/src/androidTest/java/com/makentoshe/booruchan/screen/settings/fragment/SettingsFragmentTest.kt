package com.makentoshe.booruchan.screen.settings.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private lateinit var fragment: SettingsFragment

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            fragment = SettingsFragment()
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commitNow()
        }
    }

    @Test
    fun shouldContainViewPager() {
        onView(withId(R.id.settings_viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldContainTabView() {
        onView(withId(R.id.settings_tab)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldContainToolbar() {
        onView(withId(R.id.settings_toolbar)).check(matches(isDisplayed()))
    }

}