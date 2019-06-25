package com.makentoshe.booruview

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BooruFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity

    private val booru = mockk<Booru>().apply {
        every { title } returns "Testbooru"
    }

    private val tags = setOf<Tag>(mockk(), mockk())

    private val navigator = mockk<BooruFragmentNavigator>().apply {
        // navigator mocks
        every { setNavigator(any()) } just Runs
        every { removeNavigator() } just Runs

        every { navigateToPosts(any()) } just Runs
    }

    @Before
    fun init() {
        activity = rule.launchActivity(null)

        val fragment = BooruFragment.build(BooruTransitionData(booru, tags), navigator)

        instrumentation.runOnMainSync {
            activity.supportFragmentManager.beginTransaction()
                .add(com.makentoshe.booruview.R.id.app_container, fragment).commitNow()
        }
    }

    @Test
    fun shouldNavigateToPostsAtTheStartup() {
        verify { navigator.navigateToPosts(any()) }
    }

}

