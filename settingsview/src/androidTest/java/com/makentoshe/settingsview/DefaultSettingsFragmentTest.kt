package com.makentoshe.settingsview

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.settings.common.NsfwAlertSettingController
import com.makentoshe.settings.common.NsfwSettingController
import com.makentoshe.settings.common.RealmSettingsBuilder
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.fragment.DefaultSettingsFragment
import io.mockk.*
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultSettingsFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var settingsBuilder: SettingsBuilder
    private lateinit var activity: Activity
    private lateinit var alertController: NsfwAlertSettingController
    private lateinit var nsfwController: NsfwSettingController

    @Before
    fun init() {
        alertController = mockk()
        every { alertController.value = any() } just Runs
        nsfwController = mockk()
        every { nsfwController.value = any() } just Runs
        every { nsfwController.value } returns false

        settingsBuilder = mockk()
        every { settingsBuilder.buildNsfw() } returns nsfwController
        every { settingsBuilder.buildNsfwAlert() } returns alertController

        val fragment = DefaultSettingsFragment.build(settingsBuilder)

        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            rule.activity.supportFragmentManager.beginTransaction()
                .add(com.makentoshe.settingsview.R.id.app_container, fragment).commitNow()
        }
    }

    @Test
    fun shouldChangeSetting() {
        every { alertController.value } returns false
        every { nsfwController.value } returns false

        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).perform(click()).noActivity()
        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).check(matches(isChecked()))

        verify { nsfwController.value = true }

        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).perform(click())
        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).check(matches(isNotChecked()))

        verify { nsfwController.value = false }
    }

    @Test
    fun shouldEnableNsfwSettingOnAlertDialogPositiveButtonPressed() {
        every { alertController.value } returns true
        every { nsfwController.value } returns false

        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        verify { nsfwController.value = true }
    }

    @Test
    fun shouldDisableNsfwSettingOnAlertDialogNegativeButtonPressed() {
        every { alertController.value } returns true
        every { nsfwController.value } returns true

        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).perform(click()).noActivity()
        onView(withId(android.R.id.button2)).perform(click())

        verify { nsfwController.value = false }
    }

    @Test
    fun shouldDisableNsfwSettingOnAlertDialogCancel() {
        every { alertController.value } returns true
        every { nsfwController.value } returns true

        onView(withId(com.makentoshe.settingsview.R.id.nsfw_setting_target)).perform(click())
        pressBack()

        verify { nsfwController.value = false }
    }

}