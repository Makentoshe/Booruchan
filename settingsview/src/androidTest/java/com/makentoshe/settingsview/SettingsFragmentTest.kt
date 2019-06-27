package com.makentoshe.settingsview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.settings.common.NsfwAlertSettingController
import com.makentoshe.settings.common.NsfwSettingController
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.fragment.SettingsFragment
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var settingsBuilder: SettingsBuilder

    private lateinit var alertController: NsfwAlertSettingController
    private lateinit var nsfwController: NsfwSettingController

    @Before
    fun init() {
        alertController = mockk()
        every { alertController.value = any() } just Runs
        every { alertController.value } returns false
        nsfwController = mockk()
        every { nsfwController.value = any() } just Runs
        every { nsfwController.value } returns false

        settingsBuilder = mockk()
        every { settingsBuilder.buildNsfw() } returns nsfwController
        every { settingsBuilder.buildNsfwAlert() } returns alertController

        activity = rule.launchActivity(null)

        val fragment = SettingsFragment.build(settingsBuilder)

        instrumentation.runOnMainSync {
            rule.activity.supportFragmentManager
                .beginTransaction().add(com.makentoshe.settingsview.R.id.app_container, fragment).commitNow()
        }
    }

    @Test
    fun shouldContainTitle() {
        onView(withText(com.makentoshe.settingsview.R.string.title)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldContainDefaultSettingTitle() {
        onView(withId(com.makentoshe.settingsview.R.id.settings_tab)).check { view, _ ->
            view as TabLayout
            val v = view.getTabAt(0)!!
            val text = instrumentation.targetContext.getString(com.makentoshe.settingsview.R.string.default_setting)
            assertEquals(text, v.text)
        }
    }

    @Test
    fun shouldFillTitleWithElements() {
        onView(withId(com.makentoshe.settingsview.R.id.settings_tab)).check { view, _ ->
            view as TabLayout
            assertEquals(1, view.tabCount)
        }
    }

    @Test
    fun shouldContainViewPager() {
        onView(withId(com.makentoshe.settingsview.R.id.settings_viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFillViewPagerWithElements() {
        onView(withId(com.makentoshe.settingsview.R.id.settings_viewpager)).check { view, _ ->
            view as ViewPager
            assertEquals(1, view.adapter!!.count)
        }
    }
}