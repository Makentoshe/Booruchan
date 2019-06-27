package com.makentoshe.startview

import android.widget.ListView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.api.repository.Repository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settings.common.NsfwSettingController
import io.mockk.*
import org.hamcrest.CoreMatchers.anything
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    private val booru1 = mockk<Booru>().apply {
        every { isNsfw } returns true
        every { title } returns "booru1"
    }

    private val booru2 = mockk<Booru>().apply {
        every { isNsfw } returns false
        every { title } returns "booru2"
    }

    @Before
    fun init() {
        activity = rule.launchActivity(null)
    }

    @Test
    fun shouldNotDisplayNSFWBooru() {
        val nsfwController = mockk<NsfwSettingController>()
        every { nsfwController.value } returns false

        val settingsBuilder = mockk<SettingsBuilder>()
        every { settingsBuilder.buildNsfw() } returns nsfwController

        buildFragment(settingsBuilder, mockk())

        onView(withId(com.makentoshe.startview.R.id.start_content_listview)).check { view, _ ->
            view as ListView
            val adapter = view.adapter
            assertEquals(1, adapter.count)
            assertEquals(booru1.title, adapter.getItem(0).toString())
        }
    }

    @Test
    fun shouldDisplayNSFWBooru() {
        val nsfwController = mockk<NsfwSettingController>()
        every { nsfwController.value } returns true

        val settingsBuilder = mockk<SettingsBuilder>()
        every { settingsBuilder.buildNsfw() } returns nsfwController

        buildFragment(settingsBuilder, mockk())

        onView(withId(com.makentoshe.startview.R.id.start_content_listview)).check { view, _ ->
            view as ListView
            val adapter = view.adapter
            assertEquals(2, adapter.count)
            assertEquals(booru1.title, adapter.getItem(0).toString())
            assertEquals(booru2.title, adapter.getItem(1).toString())
        }
    }

    @Test
    fun shouldNavigateToSettingsScreen() {
        val nsfwController = mockk<NsfwSettingController>()
        every { nsfwController.value } returns true

        val settingsBuilder = mockk<SettingsBuilder>()
        every { settingsBuilder.buildNsfw() } returns nsfwController

        val navigator = mockk<StartFragmentNavigator>()
        every { navigator.navigateToSettingsScreen() } just Runs

        buildFragment(settingsBuilder, navigator)

        onView(withId(com.makentoshe.startview.R.id.start_toolbar_overflow)).perform(click())
        onView(withText(com.makentoshe.startview.R.string.settings)).inRoot(isPlatformPopup()).perform(click())

        verify { navigator.navigateToSettingsScreen() }
    }

    @Test
    fun shouldNavigateToBooruScreen() {
        val nsfwController = mockk<NsfwSettingController>()
        every { nsfwController.value } returns true

        val settingsBuilder = mockk<SettingsBuilder>()
        every { settingsBuilder.buildNsfw() } returns nsfwController

        val navigator = mockk<StartFragmentNavigator>()
        every { navigator.navigateToBooruScreen(any()) } just Runs

        buildFragment(settingsBuilder, navigator)

        onData(anything()).inAdapterView(withId(com.makentoshe.startview.R.id.start_content_listview))
            .atPosition(0).perform(click())

        verify { navigator.navigateToBooruScreen(any()) }
    }

    private fun buildFragment(settingsBuilder: SettingsBuilder, navigator: StartFragmentNavigator) {
        val repository = mockk<Repository<Any, List<Booru>>>()
        every { repository.get(any()) } returns listOf(booru1, booru2)

        val fragment = StartFragment.build(settingsBuilder, navigator, repository)

        instrumentation.runOnMainSync {
            activity.supportFragmentManager.beginTransaction()
                .add(com.makentoshe.settings.R.id.app_container, fragment).commitNow()
        }
    }

}
