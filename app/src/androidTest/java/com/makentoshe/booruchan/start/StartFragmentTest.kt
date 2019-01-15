package com.makentoshe.booruchan.start

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.booru.BooruFragment
import com.makentoshe.booruchan.settings.SettingsFragment
import org.hamcrest.CoreMatchers.anything
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)
    private var booruPosition: Int = -1

    @Before
    fun init() {
        //add mocked booru instance into list
        val booru = Mockbooru()
        Booruchan.INSTANCE.booruList.add(booru)
        //define index of mocked booru
        booruPosition = Booruchan.INSTANCE.booruList.indexOf(booru)
        activityTestRule.launchActivity(null)
    }

    @Test
    fun should_contain_StartFragment_at_the_startup() {
        activityTestRule.activity.containsFragment<StartFragment>()
    }

    @Test
    fun should_navigate_to_SettingsFragment() {
        //call overflow menu
        onView(withId(R.id.toolbar_container_overflow)).perform(ViewActions.click())
        //select "settings" element
        onView(withText(R.string.settings)).perform(ViewActions.click())
        //check that the all good and we can see SettingsFragment
        activityTestRule.activity.containsFragment<SettingsFragment>()
    }

    @Test
    fun should_contain_appname_in_the_toolbar_title() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun should_navigate_to_BooruFragment() {
        //click on mocked booru
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(booruPosition).perform(click())
        //can we see BooruFragment?
        activityTestRule.activity.containsFragment<BooruFragment>()
    }

    @After
    fun destroy() {
        Booruchan.INSTANCE.booruList.removeAt(booruPosition)
        booruPosition = -1
    }
}