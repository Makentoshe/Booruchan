package com.makentoshe.booruchan.screen.samples

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoFragment
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.assertEquals
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers


class SampleFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)

    private lateinit var activity: AppActivity
    private var position = -1

    @Before
    fun init() {

        Booruchan.INSTANCE.booruList.add(Mockbooru::class.java)
        position = Booruchan.INSTANCE.booruList.indexOf(Mockbooru::class.java)
        activity = activityTestRule.launchActivity(null)
        activity.setMockbooruFactory()
        Espresso.onData(Matchers.anything())
            .inAdapterView(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.posts_page_gridview),
                    ViewMatchers.isCompletelyDisplayed()
                )
            )
            .atPosition(1)
            .perform(ViewActions.click()).noActivity()
    }

    @Test
    fun should_navigate_to_info_screen_info() {
        onView(withId(R.id.bottombar_infoitem)).perform(click())

        activity.containsFragment<SampleInfoFragment>()

        onView(withId(R.id.sampleinfo_viewpager)).check { view, _ ->
            view as ViewPager
            assertEquals(0, view.currentItem)
        }
    }

    @Test
    fun should_navigate_to_info_screen_tags() {
        onView(withId(R.id.bottombar_tagsitem)).perform(click())

        activity.containsFragment<SampleInfoFragment>()

        onView(withId(R.id.sampleinfo_viewpager)).check { view, _ ->
            view as ViewPager
            assertEquals(1, view.currentItem)
        }
    }

    @After
    fun after() {
        Booruchan.INSTANCE.booruList.removeAt(position)
        position = -1
    }
}
