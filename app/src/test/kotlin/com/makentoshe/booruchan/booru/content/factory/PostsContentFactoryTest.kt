package com.makentoshe.booruchan.booru.content.factory

import com.makentoshe.booruchan.common.Activity
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostsContentFactoryTest {

    @Test
    fun `should create gallery`() {
        val activity = Robolectric.setupActivity(ActivityImpl::class.java)
        val gallery = PostsContentFactory(mockk()).createContent(activity)
        assertNotNull(gallery)
    }

    class ActivityImpl: Activity()

}