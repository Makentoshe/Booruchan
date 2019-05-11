package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.posts.container.model.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.container.view.PostsUi
import io.mockk.mockk
import io.mockk.spyk
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomBarCenterControllerTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var controller: BottomBarCenterController
    private lateinit var viewpager: ViewPager
    private lateinit var searchController: SearchController

    @Before
    fun init() {
        searchController = spyk(object : SearchController {
            override fun startSearch(tags: Set<Tag>) = Unit
            override fun onSearchStarted(action: (Set<Tag>) -> Unit) = Unit
        })

        activity = rule.launchActivity(null)
        controller = BottomBarCenterController(searchController)

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
    fun shouldDisplayPageNumber() {
        assertEquals(1, viewpager.currentItem)
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))

        activity.runOnUiThread { viewpager.currentItem = 39 }
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("39")))
    }

    @Test
    fun shouldChangePageNumberOnLeftSwipe() {
        assertEquals(1, viewpager.currentItem)
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))

        onView(withId(R.id.posts_viewpager)).perform(swipeRight())
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("0")))
    }

    @Test
    fun shouldChangePageNumberOnRightSwipe() {
        assertEquals(1, viewpager.currentItem)
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))

        onView(withId(R.id.posts_viewpager)).perform(swipeLeft())
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("2")))
    }
}
