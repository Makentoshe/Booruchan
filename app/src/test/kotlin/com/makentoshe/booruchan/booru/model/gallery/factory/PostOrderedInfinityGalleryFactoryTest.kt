package com.makentoshe.booruchan.booru.model.gallery.factory

import com.makentoshe.booruchan.common.Activity
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostOrderedInfinityGalleryFactoryTest {

    @Test
    fun `should create gallery`() {
        val activity = Robolectric.setupActivity(ActivityImpl::class.java)
        val gallery = PostOrderedInfinityGalleryFactory(mockk()).createGallery(activity)
        assertNotNull(gallery)
    }

    class ActivityImpl: Activity()

}