package com.makentoshe.booruchan.postpreview

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.PostSamplesFragment
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostPageFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)
    private var booruPosition: Int = -1
    private lateinit var booru: Mockbooru

    @Before
    fun init() {
        //add mocked booru instance into list
        booru = Mockbooru()
        Booruchan.INSTANCE.booruList.add(booru)
        //define index of mocked booru
        booruPosition = Booruchan.INSTANCE.booruList.indexOf(booru)
        activityTestRule.launchActivity(null)
    }

    @Test
    fun should_start_samples_screen_on_element_click() {
        onMockedBooru()
        onData(anything())
            .inAdapterView(allOf(withId(R.id.postpreviewpage_grid), isCompletelyDisplayed()))
            .atPosition(0)
            .perform(ViewActions.click())

        val fragment = activityTestRule.activity.getFragment<PostSamplesFragment>()
        assertNotNull(fragment)
    }

    @Test
    fun should_show_error_message_when_posts_loading_failed() {
        onMockedBooru(false)
//        onView(allOf(withId(R.id.postpreviewpage_grid), isCompletelyDisplayed())).check(matches(not(isDisplayed())))
        onView(
            allOf(
                withId(R.id.postpreviewpage_messageview),
                isCompletelyDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

    @After
    fun clear() {
        Booruchan.INSTANCE.booruList.removeAt(booruPosition)
        booruPosition = -1
    }

    private fun onMockedBooru(posts: Boolean = true, previews: Boolean = true) {
        //click on mocked booru
        booru.postsErr = !posts
        booru.previewsErr = !previews

        Espresso.onData(CoreMatchers.anything())
            .inAdapterView(ViewMatchers.withId(R.id.start_content_listview))
            .atPosition(booruPosition)
            .perform(ViewActions.click())
    }

}