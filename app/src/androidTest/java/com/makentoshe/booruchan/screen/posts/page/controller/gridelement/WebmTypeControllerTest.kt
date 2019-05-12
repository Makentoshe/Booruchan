package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream


class WebmTypeControllerTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private lateinit var imageView: ImageView
    private lateinit var webmTypeController: WebmTypeController

    @Before
    fun init() {
        imageView = ImageView(instrumentation.targetContext)
        webmTypeController = WebmTypeController(imageView)
    }

    @Test
    fun shouldContainDrawableWhenTypeWasSetUpped() {
        assertNull(imageView.drawable)
        webmTypeController.setType()
        val drawable1 = imageView.drawable
        assertNotNull(drawable1)

        val drawable2 = instrumentation.targetContext.getDrawable(com.makentoshe.booruchan.R.drawable.ic_video)!!
        drawable1.clearColorFilter()

        val bytes1 = drawable1.toByteArray()
        val bytes2 = drawable2.toByteArray()

        assertArrayEquals(bytes1, bytes2)
    }

}