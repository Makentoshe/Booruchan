package com.makentoshe.booruchan.screen.posts.container

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.anko._Toolbar
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get
import ru.terrakok.cicerone.NavigatorHolder
import java.io.Serializable

class PostsFragmentTest : KoinTest {

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
        activity = rule.launchActivity(null)

        instrumentation.runOnMainSync {
            val navigator = FragmentNavigator(activity, R.id.appcontainer)
            get<NavigatorHolder>().setNavigator(navigator)
            val fragment = PostsFragment.create(booru, setOf())
            //setup start screen
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commitNow()
        }
    }

    @Test
    fun shouldDisplayTitleAndSubtitle() {
        onView(withId(R.id.booru_toolbar)).check { toolbar, _ ->
            toolbar as _Toolbar
            assertEquals(booru.title, toolbar.title)
            assertEquals("Posts", toolbar.subtitle)
        }
    }

    @Test
    fun shouldShowSearchFragmentOnMagnifyClick() {
        onView(withId(R.id.posts_toolbar_search)).perform(click())

        val searchDialogFragment = activity.supportFragmentManager.fragments[1]
        assertEquals(SearchDialogFragment::class, searchDialogFragment::class)
    }

    @Test
    fun shouldScrollViewPagerToNextUsingBottomBar() {
        onView(withId(R.id.posts_bottombar_right)).perform(click())

        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            viewpager as ViewPager
            assertEquals(1, viewpager.currentItem)
        }
    }

    @Test
    fun shouldScrollViewPagerToPrevUsingBottomBar() {
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            (viewpager as ViewPager).currentItem = 1
        }
        onView(withId(R.id.posts_bottombar_left)).perform(click())

        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("0")))
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            viewpager as ViewPager
            assertEquals(0, viewpager.currentItem)
        }
    }

    @Test
    fun shouldScrollViewPagerToPrevUsingSwipe() {
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            (viewpager as ViewPager).currentItem = 1
        }
        onView(isRoot()).perform(swipeRight())

        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("0")))
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            viewpager as ViewPager
            assertEquals(0, viewpager.currentItem)
        }
    }

    @Test
    fun shouldScrollViewPagerToNextUsingSwipe() {
        onView(isRoot()).perform(swipeLeft())

        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))
        onView(withId(R.id.posts_viewpager)).check { viewpager, _ ->
            viewpager as ViewPager
            assertEquals(1, viewpager.currentItem)
        }
    }

    @Test
    fun shouldStartNewSearchOnResult() {
        val tags = setOf(Tag.create("sas"), Tag.create("asa"))
        val requestCode = RequestCode.search
        val intent = Intent().apply { putExtra(Set::class.java.simpleName, tags as Serializable) }
        val fragment = activity.supportFragmentManager.fragments[0]
        val adapter1 = fragment.view!!.find<ViewPager>(R.id.posts_viewpager).adapter

        activity.runOnUiThread { fragment.onActivityResult(requestCode, 0, intent) }

        Thread.sleep(2000)

        val adapter2 = fragment.view!!.find<ViewPager>(R.id.posts_viewpager).adapter

        assertNotEquals(adapter1, adapter2)
    }

    @Test
    fun shouldUpdateAdapterOnResult() {
        val requestCode = RequestCode.postpage
        val page = 2
        val fragment = activity.supportFragmentManager.fragments[0]

        activity.runOnUiThread { fragment.onActivityResult(requestCode, page, Intent()) }

        Thread.sleep(2000)

        val adapter = fragment.view!!.find<ViewPager>(R.id.posts_viewpager).adapter

        assertEquals(page, adapter!!.count)
    }
}