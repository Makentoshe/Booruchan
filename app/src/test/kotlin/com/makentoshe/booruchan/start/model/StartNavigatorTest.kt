package com.makentoshe.booruchan.start.model

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import com.makentoshe.booruchan.booru.view.BooruActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class StartNavigatorTest {

    private val startNavigator = StartNavigator()
    private lateinit var activity: AppCompatActivity

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
    }

    @Test
    fun `test starting AppSettingsActivity`() {
        val shadowActivity = Shadows.shadowOf(activity)
        startNavigator.startAppSettingsActivity(activity)
        val intent = shadowActivity.nextStartedActivityForResult
        Assert.assertEquals(intent.intent.component!!.className, AppSettingsActivity::class.java.name)
    }

    @Test
    fun `test starting BooruActivity`() {
        val shadowActivity = Shadows.shadowOf(activity)
        startNavigator.startBooruActivity(activity, "Gelbooru")
        val intent = shadowActivity.nextStartedActivityForResult
        Assert.assertEquals(intent.intent.component!!.className, BooruActivity::class.java.name)
    }

}