package com.makentoshe.booruchan.screen.settings.fragment

import android.content.SharedPreferences
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.SettingsModule
import com.makentoshe.booruchan.style
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import ru.terrakok.cicerone.Cicerone

class SettingsDefaultFragmentTest : KoinTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var fragment: SettingsDefaultFragment
    private lateinit var appSettings: AppSettings
    private lateinit var activity: TestActivity
    private lateinit var sharedPreferences: SharedPreferences
    @Before
    fun init() {
        sharedPreferences = mockk()

        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(SettingsModule.module, module {
                single { Cicerone.create(Router()) }
                single { AppSettings(sharedPreferences) }
                single { style }
                single { get<Cicerone<Router>>().router }
                single { get<Cicerone<Router>>().navigatorHolder }
            })
        }
        appSettings = get()
        activity = rule.launchActivity(null)
    }

    @Test
    fun shouldContainNsfwSetting() {
        every { sharedPreferences.getBoolean("nsfw", any()) } returns false
        every { sharedPreferences.getBoolean("alert", any()) } returns false

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        onView(withId(R.id.setting_nsfw)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisableSettingAfterUserAgreementDecline() {
        Log.i(this::class.java.name, "shouldDisableSettingAfterUserAgreementDecline")

        every { sharedPreferences.getBoolean("nsfw", any()) } returns false
        every { sharedPreferences.getBoolean("alert", any()) } returns true

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click()).noActivity()
        //click on negative button
        onView(withId(android.R.id.button2)).perform(click()).noActivity()
        //check checkbox is not checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isNotChecked())).noActivity()
    }

    @Test
    fun shouldEnableSettingAfterUserAgreementAccept() {
        Log.i(this::class.java.name, "shouldEnableSettingAfterUserAgreementAccept")

        every { sharedPreferences.getBoolean("nsfw", any()) } returns false
        every { sharedPreferences.getBoolean("alert", any()) } returns true

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on negative button
        onView(withId(android.R.id.button1)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldEnableNsfwSettingIfUserAgreementAlreadyAccepted() {
        Log.i(this::class.java.name, "shouldEnableNsfwSettingIfUserAgreementAlreadyAccepted")

        every { sharedPreferences.getBoolean("nsfw", any()) } returns false
        every { sharedPreferences.getBoolean("alert", any()) } returns false

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldEnableNsfwOnRootClickIfUserAgreementAlreadyAccepted() {
        Log.i(this::class.java.name, "shouldEnableNsfwOnRootClickIfUserAgreementAlreadyAccepted")

        every { sharedPreferences.getBoolean("nsfw", any()) } returns false
        every { sharedPreferences.getBoolean("alert", any()) } returns false

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on root
        onView(withId(R.id.setting_nsfw)).perform(click())
        //check checkbox is checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
    }

    @Test
    fun shouldDisableNsfwOnRootClickIfUserAgreementAlreadyAccepted() {
        Log.i(this::class.java.name, "shouldDisableNsfwOnRootClickIfUserAgreementAlreadyAccepted")

        every { sharedPreferences.getBoolean("nsfw", any()) } returns true
        every { sharedPreferences.getBoolean("alert", any()) } returns false

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            Log.i(this::class.java.name, "SAS")
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on root
        onView(withId(R.id.setting_nsfw)).perform(click())
        //check checkbox is not checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isNotChecked()))
    }

    @Test
    fun shouldDisableNsfwSettingIfUserAgreementAlreadyAccepted() {
        Log.i(this::class.java.name, "shouldDisableNsfwSettingIfUserAgreementAlreadyAccepted")

        every { sharedPreferences.getBoolean("alert", any()) } returns false
        every { sharedPreferences.getBoolean("nsfw", any()) } returns true

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //check checkbox is not checked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isNotChecked()))
    }

    @Test
    fun shouldDisableSettingAfterUserAgreementDeclineAndDisplayUserAgreementOnNextTry() {
        Log.i(this::class.java.name, "shouldDisableSettingAfterUserAgreementDeclineAndDisplayUserAgreementOnNextTry")

        every { sharedPreferences.getBoolean("alert", any()) } returns true
        every { sharedPreferences.getBoolean("nsfw", any()) } returns false

        every { sharedPreferences.edit().putBoolean("nsfw", any()).apply() } just Runs
        every { sharedPreferences.edit().putBoolean("alert", any()).apply() } just Runs

        instrumentation.runOnMainSync {
            fragment = SettingsDefaultFragment.create(0)
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commit()
        }

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

    @After
    fun after() {
        Log.i(this::class.java.simpleName, "\n\n")
    }
}