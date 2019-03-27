package com.makentoshe.booruchan.screen.search

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.material.chip.Chip
import com.makentoshe.booruchan.*
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchDialogFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)

    private lateinit var activity: AppActivity
    private var position = -1

    @Before
    fun init() {
        Booruchan.INSTANCE.settings.setNsfw(Booruchan.INSTANCE.applicationContext, true)
        Booruchan.INSTANCE.booruList.add(Mockbooru::class.java)
        position = Booruchan.INSTANCE.booruList.indexOf(Mockbooru::class.java)
        activity = activityTestRule.launchActivity(null)
        activity.setMockbooruFactory()
        //click on search icon
        Espresso.onView(ViewMatchers.withId(R.id.posts_toolbar_search)).perform(ViewActions.click())
    }

    @Test
    fun title_should_be_displayed() {
        onView(withText(R.string.start_new_search)).check(matches(isDisplayed()))
    }

    @Test
    fun tag_should_be_added_on_autocomplete_tip_click() {
        //type some text and displays tips
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText("sa"))
        //tip text will be saved
        lateinit var title: String
        //get adapter for this tips and click at the position
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(0)
            //get view than will be clicked
            .check { view, _ ->
                //save tip text to string
                title = (view as TextView).text.toString()
            }.perform(click())
        //group should contain chip with text
        onView(withText(title)).check(matches(isDisplayed()))
    }

    @Test
    fun tag_should_be_added_after_space_character() {
        val text = "test"
        //type some text
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText(text.plus(" ")))
        //group should contain chip with text
        onView(withText(text)).check(matches(isDisplayed()))
    }

    @Test
    fun tag_should_be_added_after_ime_action() {
        val text = "ime_test"
        //type some text and press ime action - we will be navigated to posts screen
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText(text), pressImeActionButton())
        //return back to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.posts_toolbar_search)).perform(ViewActions.click())
        //group should contain chip with text
        onView(withText(text)).check(matches(isDisplayed()))
    }

    @Test
    fun should_remove_tag_on_close_icon_click() {
        val text = "fo"
        //type some text
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText(text.plus(" ")))
        //group should contain chip with text
        onView(withText(text)).check(matches(isDisplayed()))
        //click on chip's close icon
        onView(withText(text)).check { chip, _ ->
            chip as Chip
            chip.performCloseIconClick()
        }
        //group should not contain chip with text
        try {
            onView(withText(text))
        } catch (e: NoMatchingViewException) {
            //no view with text "fo"
        }
    }

    @Test
    fun should_invert_tag_on_click() {
        val text = "tag"
        //type some text
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText(text.plus(" ")))
        //group should contain chip with text
        val chipView = onView(withText(text)).check(matches(isDisplayed()))
        //click on chip's body
        chipView.check { view, _ ->
            view as Chip
            view.performClick()
        }
        //contains inverted tag
        onView(withText('-'.plus(text))).check(matches(isDisplayed()))
    }

    @Test
    fun should_uninvert_tag_on_click() {
        val text = "-tag"
        //type some text
        onView(withId(R.id.searchDialog_delayAutocompleteEditText)).perform(typeText(text.plus(" ")))
        //group should contain chip with text
        val chipView = onView(withText(text)).check(matches(isDisplayed()))
        //click on chip's body
        chipView.check { view, _ ->
            view as Chip
            view.performClick()
        }
        //contains uninverted tag
        onView(withText(text.substring(1))).check(matches(isDisplayed()))
    }

    @After
    fun after() {
        Booruchan.INSTANCE.booruList.removeAt(position)
        position = -1
    }

}