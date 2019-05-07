package com.makentoshe.booruchan.screen.settings.model

import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.settings.fragment.SettingsPageFragment
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class SettingsViewPagerAdapterTest : KoinTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val view = activity.find<ViewGroup>(R.id.appcontainer)
            val viewpager = ViewPager(activity).apply { id = R.id.settings_viewpager }
            view.addView(viewpager)
            val adapter = SettingsViewPagerAdapter(activity.supportFragmentManager)
            viewpager.adapter = adapter
        }
    }

    @Test
    fun shouldContainsCountPages() {
        val adapter = activity.find<ViewPager>(R.id.settings_viewpager).adapter!!
        assertEquals(1, adapter.count)
    }

    @Test
    fun shouldCreateFragments() {
        val adapter = activity.find<ViewPager>(R.id.settings_viewpager).adapter!! as FragmentPagerAdapter
        assertEquals(SettingsPageFragment::class.java, adapter.getItem(0)::class.java)
        assertEquals(SettingsPageFragment::class.java, adapter.getItem(-1)::class.java)
        assertEquals(SettingsPageFragment::class.java, adapter.getItem(393939)::class.java)
    }

    @Test
    fun shouldContainCorrectPageTitles() {
        val adapter = activity.find<ViewPager>(R.id.settings_viewpager).adapter!!
        val screenBuilder = get<SettingsScreenBuilder>()
        //default title
        assertEquals(screenBuilder.build(0).screenKey, adapter.getPageTitle(0))
    }
}