package com.makentoshe.booruchan.booru.content.posts

import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostsContentTest {

    @Test
    fun createView() {
        val viewModel = ContentViewModel(Gelbooru())
        val fragment = PostsContent(mockk()).createView(viewModel)
        Assert.assertNotNull(fragment)
    }

}