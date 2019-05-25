package com.makentoshe.booruchan.screen.start.controller

import android.content.SharedPreferences
import android.view.ViewGroup
import android.widget.ListView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get


class StartContentControllerTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val nsfwBooru1 = mockk<Booru>().apply {
        every { nsfw } returns true
        every { title } returns "NsfwBooru1"
    }

    private val nsfwBooru2 = mockk<Booru>().apply {
        every { nsfw } returns true
        every { title } returns "NsfwBooru2"
    }

    private val booru = mockk<Booru>().apply {
        every { nsfw } returns false
        every { title } returns "Booru"
    }

    private val list = listOf(nsfwBooru1, nsfwBooru2, booru)

    private lateinit var activity: TestActivity

    @Before
    fun init() {
        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(module {
                factory { spyk(StartScreenNavigator(setOf())) }
                factory { StartContentController(list) }
            })
        }
        activity = rule.launchActivity(null)
    }

    @Test
    fun shouldDisplayAllBoorusIfNsfwSettingEnabled() {
        //mock nsfw as true
        bindController()
        //check
        onView(withId(R.id.start_content_listview)).check { view, _ ->
            view as ListView
            list.forEachIndexed { index, booru ->
                val title = view.adapter.getItem(index) as String
                assertEquals(booru.title, title)
            }
        }
    }

    private fun bindController() {
        instrumentation.runOnMainSync {
            val view = ListView(activity).apply {
                id = R.id.start_content_listview
            }
            activity.find<ViewGroup>(R.id.appcontainer).addView(view)
            get<StartContentController>().bindView(activity, view)
        }
    }
}

