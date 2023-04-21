package com.makentoshe.booruchan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        super.onCreate(savedInstanceState)
        setContent { MainActivityComposeContent() }
    }
}
