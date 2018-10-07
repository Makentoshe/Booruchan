package com.makentoshe.booruchan.booru.content.comments

import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentsContentTest {

    @Test
    fun createView() {
        val viewModel = ContentViewModel(Gelbooru())
        val fragment = CommentsContent(mockk()).createView(viewModel)
        assertNotNull(fragment)
    }

}