package com.makentoshe.booruchan.booru.model.content.factory

import com.makentoshe.booruchan.booru.view.BooruActivityUI.Companion.GALLERY_COMMENT
import com.makentoshe.booruchan.booru.view.BooruActivityUI.Companion.GALLERY_POST_ORD_INF
import com.makentoshe.booruchan.common.Activity
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.lang.IllegalArgumentException

@RunWith(RobolectricTestRunner::class)
class ContentFactoryTest {

    @Test
    fun `should create ordered infinity gallery with posts factory`() {
        assertNotNull(ContentFactory.createFactory(GALLERY_POST_ORD_INF, mockk()))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not create gallery factory for undefined params`() {
        assertNotNull(ContentFactory.createFactory(GALLERY_COMMENT, mockk()))
    }

    @Test
    fun `should create ordered infinity gallery with posts`() {
        val activity = Robolectric.setupActivity(ActivityImpl::class.java)
        val factory = ContentFactory.createFactory(GALLERY_POST_ORD_INF, mockk())
        assertNotNull(factory.createContent(activity))
    }

    class ActivityImpl: Activity()

}