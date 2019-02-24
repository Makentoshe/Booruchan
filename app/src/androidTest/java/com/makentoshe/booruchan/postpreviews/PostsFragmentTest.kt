package com.makentoshe.booruchan.postpreviews

import android.view.Gravity
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.booru.BooruFragment
import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.booruchan.postpreviews.view.DelayAutocompleteEditText
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.*
import org.junit.Assert.*

class PostsFragmentTest {
    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)
    private var booruPosition: Int = -1
    private lateinit var postsFragment: PostsFragment
    private lateinit var booruFragment: BooruFragment

    @Before
    fun init() {
        //add mocked booru instance into list
        val booru = Mockbooru()
        Booruchan.INSTANCE.booruList.add(booru)
        //define index of mocked booru
        booruPosition = Booruchan.INSTANCE.booruList.indexOf(booru)
        activityTestRule.launchActivity(null)
        //click on mocked booru
        Espresso.onData(CoreMatchers.anything())
            .inAdapterView(ViewMatchers.withId(R.id.start_content_listview))
            .atPosition(booruPosition)
            .perform(ViewActions.click())
        booruFragment = activityTestRule.activity.getFragment()
        postsFragment = booruFragment.getFragment()
    }

    @Test
    fun should_open_drawer_layout_on_menu_icon_click() {
        //click drawer menu icon
        onView(withId(R.id.postpreview_toolbar_container_drawermenu)).perform(click())
        //check that the drawer was opened
        onView(withId(R.id.booru_drawer)).check(matches(isOpen(Gravity.START)))
    }

    @Test
    fun should_show_search_layout_on_overflow_click() {
        //click overflow menu icon
        onView(withId(R.id.postpreview_toolbar_container_overflow)).perform(click())
        //check that the search layout is visible
        onView(withId(R.id.postpreview_search)).check(matches(isDisplayed()))
    }

    @Test
    fun should_hide_search_layout_on_overflow_click() {
        //click overflow menu icon
        should_show_search_layout_on_overflow_click()
        //click overflow menu icon again
        onView(withId(R.id.postpreview_toolbar_container_overflow)).perform(click()).noActivity()
        //check that the search layout is not visible
        onView(withId(R.id.postpreview_search)).check(matches(not(isDisplayed()))).noActivity()
    }

    @Test
    fun should_hide_search_layout_on_cover_click() {
        //click overflow menu icon
        should_show_search_layout_on_overflow_click()
        //click on cover layout
        onView(withId(R.id.postpreview_cover)).perform(click())
        //check that the search layout is not visible
        onView(withId(R.id.postpreview_search)).check(matches(not(isDisplayed())))
    }

    @Test
    @Ignore("Feature is broking or was not realized")
    fun should_hide_search_layout_on_backbutton_click() {
        //click overflow menu icon
        should_show_search_layout_on_overflow_click()

        Espresso.closeSoftKeyboard()
        //click on back button
        Espresso.pressBack()
        //check that the search layout is not visible
        onView(withId(R.id.postpreview_search)).check(matches(not(isDisplayed())))
    }

    @Test
    fun delayAutocompleteEditText_should_autocomplete_search_term() {
        val term = "term"
        delayAutocompleteEditText_perform_text_typing(term)
        //check autocomplete terms
        onView(withId(R.id.postpreview_search_container_dacet)).check { view, _ ->
            view as DelayAutocompleteEditText
            val adapter = view.adapter
            (0 until adapter.count).forEach {
                val item = adapter.getItem(it) as Tag
                assertTrue(item.name.contains(term))
            }
        }
    }

    @Test
    fun delayAutocompleteEditText_should_be_cleared_on_clear_icon_click() {
        //perform text typing
        delayAutocompleteEditText_perform_text_typing("term")
        //click to clear icon
        onView(withId(R.id.postpreview_search_container_clear)).perform(click())
        //check the edit text is empty
        check_edittext_is_empty()
    }

    @Test
    fun delayAutocompleteEditText_should_be_cleared_on_item_click() {
        //select item
        delayAutocompleteEditText_perform_item_selecting("term", 1)
        //check the edit text is empty
        check_edittext_is_empty()
    }

    @Test
    fun delayAutocompleteEditText_should_add_item_to_chipgroup_on_item_click() {
        //select item
        delayAutocompleteEditText_perform_item_selecting("test", 8)
        //check the tag added to chip group view
        onView(withId(R.id.postpreview_search_tags)).check { view, _ ->
            view as ChipGroup
            val child = view.getChildAt(0) as Chip
            assertEquals(child.text, "test8")
        }
        //check the edit text is empty
        check_edittext_is_empty()
    }

    @Test
    fun delayAutocompleteEditText_should_be_cleared_on_space_typing() {
        delayAutocompleteEditText_perform_text_typing("tag ")
        //check the edit text is empty
        check_edittext_is_empty()
    }

    @Test
    fun delayAutocompleteEditText_should_add_item_to_chipgroup_after_space_typing() {
        delayAutocompleteEditText_should_be_cleared_on_space_typing()
        //check the tag added to chip group view
        check_tag_added_to_the_chipgroup("tag")
    }

    @Test
    fun delayAutocompleteEditText_should_remove_item_from_chipgroup_on_cross_icon_click() {
        delayAutocompleteEditText_should_add_item_to_chipgroup_after_space_typing()
        //click on cross icon
        onView(withId(R.id.postpreview_search_tags)).check { view, _ ->
            view as ChipGroup
            val child = view.getChildAt(0) as Chip
            child.performCloseIconClick()
        }
        onView(withId(R.id.postpreview_search_tags)).check { view, _ ->
            view as ChipGroup
            assertEquals(0, view.childCount)
        }
    }

    @Test
    fun should_display_bottombar_correctly_on_startup() {
        //left icon should be invisible
        onView(withId(R.id.postpreview_bottombar_left)).check { view, _ ->
            assertEquals(View.INVISIBLE, view.visibility)
        }
        //center text should display "0" number
        onView(withId(R.id.postpreview_bottombar_center_textview)).check(matches(withText("0")))
        //right icon should be visible
        onView(withId(R.id.postpreview_bottombar_right)).check { view, _ ->
            assertEquals(View.VISIBLE, view.visibility)
        }
    }

    @Test
    fun should_display_left_icon_in_bottombar_if_page_is_not_zero() {
        assertEquals(2, getPostPagesFragments().size)
        //click on next page
        onView(withId(R.id.postpreview_bottombar_right)).perform(click())

        assertEquals(3, getPostPagesFragments().size)
        //left icon should be visible
        onView(withId(R.id.postpreview_bottombar_left)).check { view, _ ->
            assertEquals(View.VISIBLE, view.visibility)
        }
    }

    @Test
    fun should_hide_left_icon_in_bottombar_if_page_is_zero() {
        should_display_left_icon_in_bottombar_if_page_is_not_zero()
        //swipe right
        onView(isRoot()).perform(swipeRight())
        //left icon should be invisible
        onView(withId(R.id.postpreview_bottombar_left)).check { view, _ ->
            assertEquals(View.INVISIBLE, view.visibility)
        }
    }

    @After
    fun clear() {
        Booruchan.INSTANCE.booruList.removeAt(booruPosition)
        booruPosition = -1
    }

    private fun delayAutocompleteEditText_perform_text_typing(term: String) {
        //click overflow menu icon
        should_show_search_layout_on_overflow_click()
        //type "term" string
        onView(withId(R.id.postpreview_search_container_dacet)).perform(typeText(term))
    }

    private fun delayAutocompleteEditText_perform_item_selecting(term: String, position: Int) {
        //perform text typing
        delayAutocompleteEditText_perform_text_typing(term)
        //perform on item click
        onData(equalTo(Tag(name = "$term$position"))).inRoot(isPlatformPopup()).perform(click())
    }

    private fun check_tag_added_to_the_chipgroup(tagName: String, position: Int = 0){
        //check the tag added to chip group view
        onView(withId(R.id.postpreview_search_tags)).check { view, _ ->
            view as ChipGroup
            val child = view.getChildAt(position) as Chip
            assertEquals(child.text, tagName)
        }
    }

    private fun check_edittext_is_empty() {
        onView(withId(R.id.postpreview_search_container_dacet)).check { view, _ ->
            view as DelayAutocompleteEditText
            assertTrue(view.text.isEmpty())
        }
    }

    private fun getPostPagesFragments(): List<PostPageFragment> {
        val listOfPostPageFragments = mutableListOf<PostPageFragment>()
        val fragments = postsFragment.childFragmentManager.fragments
        (0 until fragments.size).forEach {
            val fragment = fragments[it]
            if (fragment is PostPageFragment) {
                listOfPostPageFragments.add(fragment)
            }
        }
        return listOfPostPageFragments
    }
}
