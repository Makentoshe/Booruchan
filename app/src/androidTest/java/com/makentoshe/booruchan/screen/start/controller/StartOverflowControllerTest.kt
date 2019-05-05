package com.makentoshe.booruchan.screen.start.controller

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import io.mockk.spyk
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.matchParent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

class StartOverflowControllerTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private lateinit var activity: TestActivity
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Before
    fun init() {
        stopKoin()
        startKoin {
            modules(module {
                single { spyk(Router()) }
                single { spyk(StartScreenNavigator(setOf())) }
                single { StartOverflowController() }
            })
        }
        activity = rule.launchActivity(null)
    }

    @Test
    fun shouldDisplayOverflowMenu() {
        bindController()
        //click on overflow icon
        onView(withId(R.id.start_toolbar_overflow)).perform(click())
        //check menu items is displaying
        onView(withText(R.string.settings)).check(matches(isDisplayed()))
    }

    private fun bindController() {
        instrumentation.runOnMainSync {
            val view = FrameLayout(activity).apply {
                id = R.id.start_toolbar_overflow
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(80))
            }
            activity.find<ViewGroup>(R.id.appcontainer).addView(view)

            get<StartOverflowController>().bindView(activity, view)
        }
    }
}