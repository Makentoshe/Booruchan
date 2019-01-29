package com.makentoshe.booruchan.booru

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.account.AccountFragment
import com.makentoshe.booruchan.booru.view.BooruFragment
import com.makentoshe.booruchan.posts.view.PostsFragment
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BooruFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)
    private var booruPosition: Int = -1
    private lateinit var fragment: BooruFragment

    @Before
    fun init() {
        //add mocked booru instance into list
        val booru = Mockbooru()
        Booruchan.INSTANCE.booruList.add(booru)
        //define index of mocked booru
        booruPosition = Booruchan.INSTANCE.booruList.indexOf(booru)
        activityTestRule.launchActivity(null)
        //click on mocked booru
        onData(CoreMatchers.anything())
            .inAdapterView(withId(R.id.listview))
            .atPosition(booruPosition)
            .perform(ViewActions.click())
        fragment = activityTestRule.activity.getFragment()
    }

    @Test
    fun activity_should_contain_BooruFragment() {
        //can we see BooruFragment?
        activityTestRule.activity.containsFragment<BooruFragment>()
    }

    @Test
    fun should_contain_PostFragment_at_the_startup() {
        fragment.containsFragment<PostsFragment>()
    }

    @Test
    fun should_change_inner_fragments() {
        //open drawer
        onView(withId(R.id.boorudrawer)).perform(DrawerActions.open())
        //click on account button
        onView(withId(R.id.account)).perform(ViewActions.click())
        //PostsFragment should be replaced by AccountFragment
        fragment.containsFragment<AccountFragment>()
        //click on posts button
        onView(withId(R.id.posts)).perform(ViewActions.click())
        //AccountFragment should be replaced by PostsFragment
        fragment.containsFragment<PostsFragment>()
    }

}