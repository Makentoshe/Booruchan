package com.makentoshe.booruchan.screen.settings.controller

import android.view.ViewGroup
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.settings.model.SettingsViewPagerAdapter
import com.makentoshe.booruchan.screen.settings.view.SettingsUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewPagerControllerTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var controller: SettingsViewPagerController

    @Before
    fun init() {
        activity = rule.launchActivity(null)
        controller = SettingsViewPagerController(activity.supportFragmentManager)

        instrumentation.runOnMainSync {
            val root = activity.find<ViewGroup>(R.id.appcontainer)
            val view = SettingsUi().createView(AnkoContext.create(activity))
            root.addView(view)
            controller.bindView(view)
        }

    }

    @Test
    fun shouldSetupAdapter() {
        val view = activity.find<ViewPager>(R.id.settings_viewpager)
        val adapter = view.adapter as SettingsViewPagerAdapter

        assertEquals(SettingsViewPagerAdapter::class, adapter::class)
    }
}