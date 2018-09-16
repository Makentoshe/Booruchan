package com.makentoshe.booruchan.start

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import com.makentoshe.booruchan.booru.view.BooruActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class StartViewModelTest {

    private lateinit var activity: AppCompatActivity
    private val viewModel: StartViewModel = StartViewModel()

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
    }

    @Test
    fun `get adapter with list of services`() {
        val adapter = viewModel.createAdapter(activity)
        assertNotEquals(0, adapter.count)
    }

    @Test
    fun `should start AppSettingsActivity`() {
        val shadowActivity = Shadows.shadowOf(activity)
        viewModel.clickOnOverflow(R.id.action_app_settings, activity)
        val intent = shadowActivity.nextStartedActivityForResult
        assertEquals(intent.intent.component!!.className, AppSettingsActivity::class.java.name)
    }

    @Test
    fun `should start BooruActivity`() {
        val shadowActivity = Shadows.shadowOf(activity)
        viewModel.clickOnService(activity, "Gelbooru")
        val intent = shadowActivity.nextStartedActivityForResult
        assertEquals(intent.intent.component!!.className, BooruActivity::class.java.name)
    }

}