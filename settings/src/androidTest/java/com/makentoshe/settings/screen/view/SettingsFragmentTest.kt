package com.makentoshe.settings.screen.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.settings.TestActivity
import com.makentoshe.settings.screen.fragment.SettingsFragment
import io.realm.Realm
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        Realm.init(instrumentation.targetContext)
        activity = rule.launchActivity(null)
        val fragment = SettingsFragment.Factory().build()

        instrumentation.runOnMainSync {
            activity.supportFragmentManager
                .beginTransaction()
                .add(com.makentoshe.settings.R.id.app_container, fragment)
                .commitNow()
        }
    }

    @Test
    fun shouldContainTitle() {
        onView(withText(com.makentoshe.settings.R.string.title)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldContainDefaultSettingTitle() {
        onView(withId(com.makentoshe.settings.R.id.settings_tab)).check { view, _ ->
            view as TabLayout
            val v = view.getTabAt(0)!!
            val text = instrumentation.targetContext.getString(com.makentoshe.settings.R.string.default_setting)
            assertEquals(text, v.text)
        }
    }

    @Test
    fun shouldFillTitleWithElements() {
        onView(withId(com.makentoshe.settings.R.id.settings_tab)).check { view, _ ->
            view as TabLayout
            assertEquals(1, view.tabCount)
        }
    }

    @Test
    fun shouldContainViewPager() {
        onView(withId(com.makentoshe.settings.R.id.settings_viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFillViewPagerWithElements() {
        onView(withId(com.makentoshe.settings.R.id.settings_viewpager)).check { view, _ ->
            view as ViewPager
            assertEquals(1, view.adapter!!.count)
        }
    }
}