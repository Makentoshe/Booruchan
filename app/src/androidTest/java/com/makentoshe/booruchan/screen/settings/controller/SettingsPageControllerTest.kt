package com.makentoshe.booruchan.screen.settings.controller

import android.view.ViewGroup
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.appModule
import com.makentoshe.booruchan.screen.settings.SettingsModule
import com.makentoshe.booruchan.screen.settings.fragment.SettingsDefaultFragment
import com.makentoshe.booruchan.screen.settings.view.SettingsPageUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class SettingsPageControllerTest : KoinTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(appModule, SettingsModule.module)
        }
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val root = activity.find<ViewGroup>(R.id.appcontainer)
            val view = SettingsPageUi().createView(AnkoContext.create(activity))
            root.addView(view)
        }
    }

    @Test
    fun shouldCreateSettingsPageFragmentWithDefaultSettingsFragment() {
        val position = 0
        val controller = SettingsPageController(position, activity.supportFragmentManager, get())
        controller.bindView()

        Thread.sleep(1000)

        val fragment = activity.supportFragmentManager.fragments[position]
        assertEquals(SettingsDefaultFragment::class, fragment::class)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldSas() {
        val position = 1
        val controller = SettingsPageController(position, activity.supportFragmentManager, get())
        controller.bindView()
    }
}