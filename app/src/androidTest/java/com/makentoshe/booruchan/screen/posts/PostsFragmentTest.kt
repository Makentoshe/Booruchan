package com.makentoshe.booruchan.screen.posts

import android.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.screen.booru.BooruFragment
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import com.makentoshe.booruchan.screen.start.StartFragment
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostsFragmentTest {


    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)

    private lateinit var activity: AppActivity
    private var position = -1

    @Before
    fun init() {
        Booruchan.INSTANCE.booruList.add(Mockbooru::class.java)
        position = Booruchan.INSTANCE.booruList.indexOf(Mockbooru::class.java)
        activity = activityTestRule.launchActivity(null)
        activity.getFragment<StartFragment>().booruFactory = mockBooruFactory(activity)
        //click on mocked booru
        onView(withText(Mockbooru::class.java.simpleName)).perform(click())
    }

    @Test
    fun click_on_drawermenu_should_open_drawer() {
        //click on drawer menu
        onView(withId(R.id.booru_toolbar_drawermenu)).perform(click())
        //check drawer is open
        onView(withId(R.id.booru_drawer)).check(matches(isOpen()))
    }

    @Test
    fun click_on_searchicon_should_navigate_to_search_screen() {
        //click on search icon
        onView(withId(R.id.posts_toolbar_search)).perform(click())
        //check search screen is visible
        activity.getFragment<BooruFragment>().containsFragment<SearchDialogFragment>()
    }

    @Test
    fun left_chevron_should_be_invisible_at_the_startup() {
        onView(withId(R.id.posts_bottombar_left)).check(matches(not(isDisplayed())))
    }

    @Test
    fun click_on_right_chevron_should_scroll_viewpager() {
        scrollToRightByChevrons()
        //check the current item was changed
        onView(withId(R.id.posts_viewpager)).check { view, e ->
            view as ViewPager
            assertEquals(1, view.currentItem)
        }
    }

    @Test
    fun click_on_right_chevron_should_shows_left_chevron() {
        //at the startup the left chevron in invisible
        //perform scroll to the right
        scrollToRightByChevrons()
        //check the left chevron makes visible
        onView(withId(R.id.posts_bottombar_left)).check(matches(isDisplayed()))
    }

    @Test
    fun toolbar_should_contain_booru_title() {
        onView(withId(R.id.booru_toolbar)).check { toolbar, _ ->
            toolbar as Toolbar
            assertEquals(Mockbooru::class.java.simpleName, toolbar.title)
        }
    }

    @Test
    fun toolbar_should_contain_booru_subtitle() {
        onView(withId(R.id.booru_toolbar)).check { toolbar, _ ->
            toolbar as Toolbar
            assertEquals(activity.getString(R.string.posts), toolbar.subtitle)
        }
    }

    @Test
    fun click_on_left_chevron_can_makes_itself_invisible() {
        //if we scroll to the left and the current
        //item will be first - the left chevron makes invisible
        scrollToRightByChevrons()
        //click on left chevron when current item is 1
        scrollToLeftByChevrons()
        onView(withId(R.id.posts_bottombar_left)).check(matches(not(isDisplayed())))
    }

    @Test
    fun click_on_left_chevron_should_scroll_viewpager() {
        scrollToRightByChevrons()
        //perform scroll to the left
        scrollToLeftByChevrons()
        //check the current item was changed
        onView(withId(R.id.posts_viewpager)).check { view, e ->
            view as ViewPager
            assertEquals(0, view.currentItem)
        }
    }

    @Test
    fun scroll_to_right_should_increment_textview_value() {
        //the initial value equals current item
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("0")))
        scrollToRightByChevrons()
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("1")))
    }

    @Test
    fun scroll_to_left_should_decrement_textview_value() {
        scrollToRightByChevrons()
        scrollToLeftByChevrons()
        onView(withId(R.id.posts_bottombar_center_textview)).check(matches(withText("0")))
    }

    private fun scrollToRightByChevrons() {
        //perform scroll to the right
        onView(withId(R.id.posts_bottombar_right)).perform(click())
    }

    private fun scrollToLeftByChevrons() {
        //perform scroll to the right
        onView(withId(R.id.posts_bottombar_left)).perform(click())
    }

    @After
    fun after() {
        Booruchan.INSTANCE.booruList.removeAt(position)
        position = -1
    }

}