package com.makentoshe.settings.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.settings.TestActivity
import com.makentoshe.settings.model.realm.RealmBooleanSettingController
import com.makentoshe.settings.view.controller.NsfwAlertSettingController
import com.makentoshe.settings.view.controller.NsfwSettingController
import com.makentoshe.settings.view.fragment.DefaultSettingsFragment
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

    private lateinit var activity: TestActivity
    private lateinit var configuration: RealmConfiguration
    private lateinit var realm: Realm

    private lateinit var alertController: NsfwAlertSettingController
    private lateinit var nsfwController: NsfwSettingController

    @Before
    fun init() {
        Realm.init(instrumentation.targetContext)
        configuration = RealmConfiguration.Builder().inMemory().name("InstrumentationTest").build()
        realm = Realm.getInstance(configuration)

        activity = rule.launchActivity(null)
        val fragment = DefaultSettingsFragment.Factory().build(configuration)

        alertController = NsfwAlertSettingController(RealmBooleanSettingController(configuration))
        alertController.set(true)

        nsfwController = NsfwSettingController(RealmBooleanSettingController(configuration))
        nsfwController.set(false)

        instrumentation.runOnMainSync {
            activity.supportFragmentManager
                .beginTransaction()
                .add(com.makentoshe.settings.R.id.app_container, fragment)
                .commitNow()
        }
    }

    @Test
    fun shouldChangeSetting() {
        //disable alert
        alertController.set(false)

        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).perform(click()).noActivity()
        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).check(matches(isChecked()))
        assertEquals(true, nsfwController.get())

        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).perform(click())
        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).check(matches(isNotChecked()))
        assertEquals(false, nsfwController.get())
    }

    @Test
    fun shouldEnableNsfwSettingOnAlertDialogPositiveButtonPressed() {
        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        assertEquals(true, nsfwController.get())
        assertEquals(true, alertController.get())
    }

    @Test
    fun shouldDisableNsfwSettingOnAlertDialogNegativeButtonPressed() {
        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).perform(click()).noActivity()
        onView(withId(android.R.id.button2)).perform(click())
        assertEquals(false, nsfwController.get())
        assertEquals(true, alertController.get())
    }

    @Test
    fun shouldDisableNsfwSettingOnAlertDialogCancel() {
        onView(withId(com.makentoshe.settings.R.id.nsfw_setting_target)).perform(click())
        pressBack()
        assertEquals(false, nsfwController.get())
    }

    @After
    fun after() = realm.close()
}