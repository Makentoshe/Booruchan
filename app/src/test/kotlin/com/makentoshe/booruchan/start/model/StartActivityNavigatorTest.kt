package com.makentoshe.booruchan.start.model

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import com.makentoshe.booruchan.start.view.StartActivity
import junit.framework.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class StartActivityNavigatorTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var navigator: StartActivityNavigator

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        navigator = StartActivityNavigator(activity)
    }

    @Test
    fun `navigator should start AppSettingsActivity`() {
        navigator.startAppSettingsActivity()
        val shadowActivity = Shadows.shadowOf(activity)
        val intent = shadowActivity.peekNextStartedActivity()
        Assert.assertEquals(AppSettingsActivity::class.java.name, intent.component!!.className)
    }

    @Ignore
    @Test
    fun `navigator should start BooruActivity`() {
        navigator.startAppSettingsActivity()
        val shadowActivity = Shadows.shadowOf(activity)
        val intent = shadowActivity.peekNextStartedActivity()
        Assert.assertEquals(AppSettingsActivity::class.java.name, intent.component!!.className)
    }


}