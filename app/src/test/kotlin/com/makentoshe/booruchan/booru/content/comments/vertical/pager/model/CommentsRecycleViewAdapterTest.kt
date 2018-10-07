package com.makentoshe.booruchan.booru.content.comments.vertical.pager.model

import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentsRecycleViewAdapterTest {

    @Test
    fun itemCount() {
        val list = ArrayList<Pair<Post, List<Comment>>>()
        for (i in (0..9)) {
            list.add(mockk())
        }
        val adapter = CommentsRecycleViewAdapter(list, mockk())
        assertEquals(10, adapter.itemCount)
    }



}