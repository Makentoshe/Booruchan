package com.makentoshe.booruchan.screen.start

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.appModule
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.screen.booru.BooruFragment
import com.makentoshe.booruchan.screen.booru.BooruModule
import com.makentoshe.booruchan.screen.posts.container.PostsModule
import com.makentoshe.booruchan.screen.posts.page.PostsPageModule
import com.makentoshe.booruchan.screen.settings.SettingsModule
import com.makentoshe.booruchan.screen.settings.fragment.SettingsFragment
import com.makentoshe.booruchan.screen.start.controller.StartContentController
import com.makentoshe.booruchan.screen.start.controller.StartOverflowController
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.anything
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
import ru.terrakok.cicerone.NavigatorHolder


class StartFragmentTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    private val booru = mockk<Booru>().apply {
        every { title } returns "Testbooru"
        every { nsfw } returns false
    }

    @Before
    fun init() {
        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(appModule, SettingsModule.module, BooruModule.module, PostsModule.module, PostsPageModule.module,
                module {
                    single { StartOverflowController() }
                    single { StartContentController(listOf(booru)) }
                    single { StartScreenNavigator(setOf()) }
                }
            )
        }

        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val navigator = FragmentNavigator(activity, R.id.appcontainer)
            get<NavigatorHolder>().setNavigator(navigator)
            //setup start screen
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, StartFragment()).commitNow()
        }
    }

    @Test
    fun shouldNavigateToSettingsScreen() {
        onView(withId(R.id.start_toolbar_overflow)).perform(click())
        onView(withText(R.string.settings)).perform(click())
        //the SettingsFragment is replace the StartFragment
        assertEquals(SettingsFragment::class.java, activity.supportFragmentManager.fragments[0]::class.java)
    }

    @Test
    fun shouldNavigateToBooruScreen() {
        onData(anything()).inAdapterView(withId(R.id.start_content_listview)).atPosition(0).perform(click())
        //the next fragment after the StartFragment must be the BooruFragment
        assertEquals(BooruFragment::class.java, activity.supportFragmentManager.fragments[1]::class.java)
    }
}
