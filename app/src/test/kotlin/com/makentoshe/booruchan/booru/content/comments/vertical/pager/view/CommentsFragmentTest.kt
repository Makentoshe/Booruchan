package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.comments.CommentsContent
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentsFragmentTest {

    @Test
    fun test() {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val fragment = CommentsContent(AppSettings()).createView(ContentViewModel(Gelbooru()))
        activity.supportFragmentManager
                .beginTransaction()
                .add(fragment, "SAS")
                .commitNow()
        assertEquals(1, activity.supportFragmentManager.fragments.size)
        assertNotNull(fragment.view)
    }

}