package com.makentoshe.booruchan.screen.settings

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.Mockbooru
import com.makentoshe.booruchan.R
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
        Booruchan.INSTANCE.settings.setNsfw(activity, false)
        //show overflow menu
        onView(withId(R.id.start_toolbar_overflow)).perform(click())
        //click on overflow menu item
        onView(withText(R.string.settings)).perform(click())
    }

    @Test
    fun should_show_user_agreements_dialog_on_first_click() {
        Booruchan.INSTANCE.settings.setNsfwAlert(activity, true)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //dialog contains title
        onView(withText(R.string.user_agreement)).check(matches(isDisplayed()))
        //dialog contains content
        onView(withText(R.string.user_agreement_content)).check(matches(isDisplayed()))
    }

    @Test
    fun should_uncheck_nsfw_when_user_agreements_dialog_disagree() {
        Booruchan.INSTANCE.settings.setNsfwAlert(activity, true)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on disagree button
        onView(withText(R.string.disagree)).perform(click())
        //checkbox must be unchecked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(not(isChecked())))
        assertFalse(Booruchan.INSTANCE.settings.getNsfw(activity))
        assertTrue(Booruchan.INSTANCE.settings.getNsfwAlert(activity))
    }

    @Test
    fun should_check_nsfw_when_user_agreements_dialog_agree() {
        Booruchan.INSTANCE.settings.setNsfwAlert(activity, true)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on agree button
        onView(withText(R.string.agree)).perform(click())
        //checkbox must be unchecked
        onView(withId(R.id.setting_nsfw_checkbox)).check(matches(isChecked()))
        assertTrue(Booruchan.INSTANCE.settings.getNsfw(activity))
        assertFalse(Booruchan.INSTANCE.settings.getNsfwAlert(activity))
    }

    @Test
    fun should_display_user_agreements_dialog_again_after_disagree() {
        Booruchan.INSTANCE.settings.setNsfwAlert(activity, true)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on disagree button
        onView(withText(R.string.disagree)).perform(click())
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //dialog contains title
        onView(withText(R.string.user_agreement)).check(matches(isDisplayed()))
    }

    @Test
    fun user_agreements_dialog_should_not_be_displayed_after_agree() {
        Booruchan.INSTANCE.settings.setNsfwAlert(activity, true)
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on agree button
        onView(withText(R.string.agree)).perform(click())
        assertFalse(Booruchan.INSTANCE.settings.getNsfwAlert(activity))
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
        //click on checkbox
        onView(withId(R.id.setting_nsfw_checkbox)).perform(click())
    }
}