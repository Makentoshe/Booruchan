package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.widget.ImageView
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GifTypeControllerTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private lateinit var imageView: ImageView
    private lateinit var gifTypeController: GifTypeController

    @Before
    fun init() {
        imageView = ImageView(instrumentation.targetContext)
        gifTypeController = GifTypeController(imageView)
    }

    @Test
    fun shouldContainDrawableWhenTypeWasSetUpped() {
        assertNull(imageView.drawable)
        gifTypeController.setType()
        val drawable1 = imageView.drawable
        assertNotNull(drawable1)

        val drawable2 = instrumentation.targetContext.getDrawable(com.makentoshe.booruchan.R.drawable.ic_animation)!!
        drawable1.clearColorFilter()

        val bytes1 = drawable1.toByteArray()
        val bytes2 = drawable2.toByteArray()

        assertArrayEquals(bytes1, bytes2)
    }

}