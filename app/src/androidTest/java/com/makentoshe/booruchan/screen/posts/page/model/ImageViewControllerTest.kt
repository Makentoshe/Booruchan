package com.makentoshe.booruchan.screen.posts.page.model

import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.toByteArray
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class ImageViewControllerTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var imageViewController: ImageViewController
    private lateinit var imageView: ImageView

    @Before
    fun init() {
        imageView = spyk(ImageView(instrumentation.targetContext))
        imageViewController = ImageViewController(imageView)
    }

    @Test
    fun shouldSetBitmapOnSuccess() {
        val drawable = instrumentation.targetContext.getDrawable(R.drawable.ic_download)!!
        imageViewController.onSuccess(drawable.toBitmap())

        val byteArray1 = drawable.toByteArray()
        val byteArray2 = imageView.drawable.toByteArray()

        assertArrayEquals(byteArray1, byteArray2)
    }

    @Test
    fun shouldContainPresetBitmapOnError() {
        imageViewController.onError(mockk())

        val drawable = instrumentation.targetContext.getDrawable(R.drawable.ic_alert_octagon_outline)!!
        val byteArray1 = drawable.toByteArray()
        imageView.drawable.clearColorFilter()
        val byteArray2 = imageView.drawable.toByteArray()

        assertArrayEquals(byteArray1, byteArray2)
    }

}