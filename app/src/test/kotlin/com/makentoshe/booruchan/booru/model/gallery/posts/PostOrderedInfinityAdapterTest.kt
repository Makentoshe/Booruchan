package com.makentoshe.booruchan.booru.model.gallery.posts

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.common.AdapterDataLoader
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostOrderedInfinityAdapterTest {

    @Test
    fun `on bind view holder`() {
        val imageViewMock = mockk<ImageView>()
        every { imageViewMock.setImageDrawable(any()) } just Runs

        val holderMock = mockk<PostOrderedInfinityAdapter.ViewHolder>()
        for (i in 0..2){
            every { holderMock.getPostPreviewView(i) } returns imageViewMock
        }

        val dataLoader = mockk<AdapterDataLoader>()
        every { dataLoader.getPostsData(0, any()) } just Runs

        val adapter = PostOrderedInfinityAdapter(dataLoader)
        adapter.onBindViewHolder(holderMock, 0)

        verify { dataLoader.getPostsData(0, any()) }
        verify { holderMock.getPostPreviewView(0) }
    }

    @Test
    @Ignore
    fun `a bad test of the private method - "setBitmapToImageView"`() {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val imageView = ImageView(activity)
        val bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.ic_arrow_vector)
        val adapter = PostOrderedInfinityAdapter(mockk())
        //todo how to perform it???
    }

    @Test
    fun `get item count after creation`() {
        val adapter = PostOrderedInfinityAdapter(mockk())
        assertEquals(Int.MAX_VALUE, adapter.itemCount)
    }

    @Test
    fun `view holder creation`() {
        val adapter = PostOrderedInfinityAdapter(mockk())
        val viewParent = FrameLayout(Robolectric.setupActivity(AppCompatActivity::class.java))
        val viewHolder = adapter.onCreateViewHolder(viewParent, 0)
        assertNotNull(viewHolder)
    }

}