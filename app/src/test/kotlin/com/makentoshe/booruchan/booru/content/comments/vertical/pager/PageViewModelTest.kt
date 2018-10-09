package com.makentoshe.booruchan.booru.content.comments.vertical.pager

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PageViewModelTest {

    @Test
    fun getCommentedPosts() = runBlocking {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val client = mockk<HttpClient>()
        val booru = mockk<Boor>()
        every {
            runBlocking { booru.getListOfLastCommentedPosts(1, client) }
        } returns result()
        val viewModel = PageViewModel(booru, client)
        viewModel.getCommentedPosts(1, activity) {
            assertEquals(1, it!!.size)
        }
    }

    private fun result(): List<Pair<Post, List<Comment>>> {
        val list = ArrayList<Pair<Post, List<Comment>>>()
        list.add(mockk())
        return list
    }

}