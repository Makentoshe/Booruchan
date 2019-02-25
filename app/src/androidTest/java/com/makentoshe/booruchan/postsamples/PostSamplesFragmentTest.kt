package com.makentoshe.booruchan.postsamples

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import org.hamcrest.CoreMatchers
import org.junit.*

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
        //click on mocked booru
        Espresso.onData(CoreMatchers.anything())
            .inAdapterView(ViewMatchers.withId(R.id.start_content_listview))
            .atPosition(booruPosition)
            .perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything())
            .inAdapterView(
                CoreMatchers.allOf(
                    ViewMatchers.withId(R.id.postpreviewpage_grid),
                    ViewMatchers.isCompletelyDisplayed()
                )
            )
            .atPosition(0)
            .perform(ViewActions.click())
    }

    @Test
    fun should_download_file_on_download_iconClick() {
    }

    @Test
    fun should_start_samples_screen_on_element_click() {
        val fragment = activityTestRule.activity.getFragment<PostSamplesFragment>()
        Assert.assertNotNull(fragment)
    }

    @After
    fun clear() {
        Booruchan.INSTANCE.booruList.removeAt(booruPosition)
        booruPosition = -1
    }
}