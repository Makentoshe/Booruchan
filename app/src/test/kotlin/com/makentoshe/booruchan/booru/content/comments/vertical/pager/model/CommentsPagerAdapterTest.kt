package com.makentoshe.booruchan.booru.content.comments.vertical.pager.model

import android.support.v7.app.AppCompatActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentsPagerAdapterTest {

    private lateinit var adapter: CommentsPagerAdapter

    @Before
    fun init() {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        adapter = CommentsPagerAdapter(activity.supportFragmentManager)
    }

    @Test
    fun getItem() {
        val fragment = adapter.getItem(2)
        assertNotNull(fragment)
    }

    @Test
    fun getCount() {
        assertEquals(Int.MAX_VALUE, adapter.count)
    }
}