package com.makentoshe.booruchan.screen.settings

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsFragmentTest {
    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)

    private lateinit var activity: AppActivity

    @Before
    fun init() {
        activity = activityTestRule.launchActivity(null)
        //show overflow menu
        onView(withId(R.id.start_toolbar_overflow)).perform(click())
        //click on overflow menu item
        onView(withText(R.string.settings)).perform(click())
    }

    @Test
    fun shouldChangeNSFWSetting() {
        val nsfw = Booruchan.INSTANCE.settings.getNSFW(activity)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //check value
        val nsfw2 = Booruchan.INSTANCE.settings.getNSFW(activity)
        //the click changes settings
        assertNotEquals(nsfw, nsfw2)
    }

}