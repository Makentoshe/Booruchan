package com.makentoshe.booruchan

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.ComposeView
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.ui.MainActivityComposeContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    init {
        Thread.UncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            throw e
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        screenLogInfo(this, "OnCreate: $savedInstanceState")
        super.onCreate(savedInstanceState)
        setContent { MainActivityComposeContent() }
    }
}
