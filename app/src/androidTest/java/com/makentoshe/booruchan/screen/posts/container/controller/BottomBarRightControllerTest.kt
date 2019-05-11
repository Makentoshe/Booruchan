package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.posts.container.model.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.container.view.PostsUi
import io.mockk.mockk
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomBarRightControllerTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var controller: BottomBarRightController
    private lateinit var viewpager: ViewPager
    @Before
    fun init() {
        activity = rule.launchActivity(null)
        controller = BottomBarRightController()

        instrumentation.runOnMainSync {
            val root = activity.find<ViewGroup>(R.id.appcontainer)
            val view = PostsUi().createView(AnkoContext.create(activity))
            root.addView(view)
            controller.bindView(view)
            viewpager = view.find(R.id.posts_viewpager)
            viewpager.adapter = PostsViewPagerAdapter(activity.supportFragmentManager, setOf(), mockk())
            viewpager.currentItem = 1
        }
    }

    @Test
    fun shouldScrollToRightOnClick() {
        assertEquals(1, viewpager.currentItem)

        onView(withId(R.id.posts_bottombar_right)).perform(click()).noActivity()

        assertEquals(2, viewpager.currentItem)
    }

}