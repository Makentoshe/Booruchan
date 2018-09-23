package com.makentoshe.booruchan.start.view

import android.widget.ListView
import com.makentoshe.booruchan.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StartActivityUITest {

    @Test
    fun `test on item click lambda will not cause any exceptions`() {
        val activity = Robolectric.setupActivity(StartActivity::class.java)
        val listView = activity.findViewById<ListView>(R.id.start_content)
        listView.performItemClick(
                listView.adapter.getView(0, null, null),
                0,
                listView.adapter.getItemId(0))
    }

}