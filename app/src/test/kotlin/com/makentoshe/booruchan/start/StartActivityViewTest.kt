package com.makentoshe.booruchan.start

import android.support.v7.widget.Toolbar
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.styles.Style
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StartActivityViewTest {

    private lateinit var view: View
    private lateinit var style: Style

    @Before
    fun init() {
        val activity = Robolectric.setupActivity(StartActivity::class.java)
        view = activity.window.decorView
        style = activity.style
    }

    @Test
    fun `should contain main view`() {
        assertNotNull(view)
    }

    @Test
    fun `test toolbar title`() {
        val toolbar = view.findViewById<Toolbar>(R.id.activity_start_toolbar)
        val expectedToolbarTitle = view.context.getString(R.string.app_name)
        assertEquals(expectedToolbarTitle, toolbar.title)
    }

}