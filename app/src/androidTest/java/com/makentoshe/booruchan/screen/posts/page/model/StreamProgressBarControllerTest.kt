package com.makentoshe.booruchan.screen.posts.page.model

import android.os.Build
import android.view.View
import android.widget.ProgressBar
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StreamProgressBarControllerTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var streamProgressBarController: StreamProgressBarController
    private lateinit var progressBar: ProgressBar

    @Before
    fun init() {
        progressBar = spyk(ProgressBar(instrumentation.targetContext))
        progressBar.max = 100
        streamProgressBarController = StreamProgressBarController(progressBar)
    }

    @Test
    fun shouldBeInvisibleOnComplete() {
        streamProgressBarController.onComplete()

        assertEquals(View.GONE, progressBar.visibility)
    }

    @Test
    fun shouldBeInvisibleOnError() {
        streamProgressBarController.onError()

        assertEquals(View.GONE, progressBar.visibility)
    }

    @Test
    fun shouldConvertAndDisplayProgressFromFloatToInt() {
        val progress = 0.5f
        streamProgressBarController.onProgress(progress)

        assertEquals(View.VISIBLE, progressBar.visibility)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            verify { progressBar.setProgress(any(), any()) }
        } else {
            verify { progressBar.progress = any() }
        }
    }
}