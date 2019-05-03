package com.makentoshe.booruchan.screen.start

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.screen.booru.BooruFragment
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.container.SettingsFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)

    private lateinit var activity: AppActivity
    private var position = -1

    @Before
    fun init() {
        Booruchan.INSTANCE.settings.setNsfw(Booruchan.INSTANCE.applicationContext, true)
        AppSettings.setNsfw(Booruchan.INSTANCE.applicationContext, true)
        Booruchan.INSTANCE.booruList.add(Mockbooru::class.java)
        position = Booruchan.INSTANCE.booruList.indexOf(Mockbooru::class.java)
        activity = activityTestRule.launchActivity(null)
    }

    @Test
    fun should_contain_StartFragment_at_the_startup() {
        activity.containsFragment<StartFragment>()
    }

    @Test
    fun should_navigate_to_settings_screen() {
        //show overflow menu
        onView(withId(R.id.start_toolbar_overflow)).perform(click())
        //click on overflow menu item
        onView(withText(R.string.settings)).perform(click())

        activity.containsFragment<SettingsFragment>()
    }

    @Test
    fun should_navigate_to_booru_screen() {
        activity.getFragment<StartFragment>().booruFactory = mockBooruFactory(activity)
        //click on mocked booru
        onView(withText(Mockbooru::class.java.simpleName)).perform(click())

        activity.containsFragment<BooruFragment>()
    }

    @After
    fun after() {
        Booruchan.INSTANCE.booruList.removeAt(position)
        position = -1
    }
}