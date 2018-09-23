package com.makentoshe.booruchan.booru.model.content.factory

import com.makentoshe.booruchan.common.Activity
import io.mockk.mockk
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentContentFactoryTest {

    @Test
    fun `should create content`() {
        val activity = Robolectric.setupActivity(ActivityImpl::class.java)
        val content = CommentContentFactory(mockk()).createContent(activity)
        Assert.assertNotNull(content)
    }

    class ActivityImpl: Activity()

}