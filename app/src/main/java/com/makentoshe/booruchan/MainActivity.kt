package com.makentoshe.booruchan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.controller.setOnDisposableDestinationChangedListenerEffect
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.ui.MainActivityComposeContent
import com.makentoshe.booruchan.ui.MainActivityDrawerContent
import com.makentoshe.booruchan.ui.MainActivityNavigationContent
import com.makentoshe.booruchan.ui.rememberDrawerState
import com.makentoshe.library.uikit.theme.BooruchanTheme
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
