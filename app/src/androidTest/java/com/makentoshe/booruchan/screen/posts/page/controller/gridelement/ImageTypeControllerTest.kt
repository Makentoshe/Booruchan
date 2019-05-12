package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.view.View
import android.widget.ImageView
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImageTypeControllerTest{

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private lateinit var imageView: ImageView
    private lateinit var gifTypeController: ImageTypeController

    @Before
    fun init() {
        imageView = ImageView(instrumentation.targetContext)
        gifTypeController = ImageTypeController(imageView)
    }

    @Test
    fun shouldContainDrawableWhenTypeWasSetUpped() {
        assertNull(imageView.drawable)
        gifTypeController.setType()
        assertNull(imageView.drawable)
        assertEquals(View.GONE, imageView.visibility)
    }

}
