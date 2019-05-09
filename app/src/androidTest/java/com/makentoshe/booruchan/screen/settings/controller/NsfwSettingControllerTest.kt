package com.makentoshe.booruchan.screen.settings.controller

import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.view.SettingsDefaultUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.junit.*
import org.junit.Assert.assertEquals
import org.koin.test.KoinTest

class NsfwSettingControllerTest {

    private val identifier = this::class.java.simpleName

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var controller: NsfwSettingController
    private lateinit var appSettings: AppSettings

    @Before
    fun init() {
        appSettings = AppSettings(identifier)
        appSettings.default.alert = true
        appSettings.default.nsfw = false

        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val root = activity.find<ViewGroup>(R.id.appcontainer)
            val view = SettingsDefaultUi().createView(AnkoContext.create(activity))
            root.addView(view)

            val state = NsfwStateController(appSettings)
            val alert = NsfwAlertController(activity.supportFragmentManager)
            controller = NsfwSettingController(alert, state)
            controller.bindView(view)
        }
    }

    @Test
    @Ignore("Some troubles with settings.default")
    fun shouldEnableSettingAfterUserAgreementAccept() {
        appSettings.default.alert = true
        appSettings.default.nsfw = false

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on positive button
        onView(withId(android.R.id.button1)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))

        assertEquals(true, appSettings.default.nsfw)
    }

    @Test
    fun shouldDisableSettingAfterUserAgreementDecline() {
        appSettings.default.alert = true
        appSettings.default.nsfw = true

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on negative button
        onView(withId(android.R.id.button2)).perform(click())
        //check checkbox is not checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isNotChecked()))

        assertEquals(false, appSettings.default.nsfw)
    }

    @Test
    fun shouldDisableSettingAfterUserAgreementDeclineAndDisplayUserAgreementOnNextTry() {
        appSettings.default.alert = true
        appSettings.default.nsfw = false
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on negative button
        onView(withId(android.R.id.button2)).perform(click())
        //check checkbox is not checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isNotChecked()))
        //click on checkbox again
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on positive button
        onView(withId(android.R.id.button1)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldEnableNsfwSettingIfUserAgreementAlreadyAccepted() {
        appSettings.default.alert = false
        appSettings.default.nsfw = false
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    @Ignore("Some troubles with settings.default")
    fun shouldDisableNsfwSettingIfUserAgreementAlreadyAccepted() {
        appSettings.default.alert = false
        appSettings.default.nsfw = true

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldEnableNsfwOnRootClickIfUserAgreementAlreadyAccepted() {
        appSettings.default.alert = false
        appSettings.default.nsfw = true
        //click on root
        onView(withId(R.id.setting_nsfw)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldDisableNsfwOnRootClickIfUserAgreementAlreadyAccepted() {
        appSettings.default.alert = false
        appSettings.default.nsfw = false
        //click on root
        onView(withId(R.id.setting_nsfw)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

}