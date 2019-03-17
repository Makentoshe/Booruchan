package com.makentoshe.booruchan.screen.booru

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.screen.account.AccountFragment
import com.makentoshe.booruchan.screen.account.AccountScreen
import com.makentoshe.booruchan.screen.posts.PostsFragment
import com.makentoshe.booruchan.screen.start.StartFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BooruFragmentTest {

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
//
//    @Test
//    fun click_on_drawermenu_should_open_drawer() {
//        //click on drawer menu
//        onView(withId(R.id.booru_toolbar_drawermenu)).perform(click())
//        //check drawer is open
//        onView(withId(R.id.booru_drawer)).check(matches(DrawerMatchers.isOpen()))
//    }
//
    @Test
    fun should_display_posts_screen_on_start() {
        //check current screen is posts
        activity.getFragment<BooruFragment>().containsFragment<PostsFragment>()
        //swipe to open drawer
//        onView(withId(R.id.booru_drawer)).perform(open())
        //click to the account screen
    }

    @Test
    fun should_change_current_screen_to_account_screen() {
        //swipe to open drawer
        onView(withId(R.id.booru_drawer)).perform(open())
        //click to the account screen
        onView(withId(R.id.booru_drawer_panel_account)).perform(click())
        //check current screen is account
        activity.getFragment<BooruFragment>().containsFragment<AccountFragment>()
    }

    @After
    fun after() {
        Booruchan.INSTANCE.booruList.removeAt(position)
        position = -1
    }

}