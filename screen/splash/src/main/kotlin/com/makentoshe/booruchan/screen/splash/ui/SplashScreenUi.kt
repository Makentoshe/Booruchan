package com.makentoshe.booruchan.screen.splash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.splash.viewmodel.SplashScreenEvent
import com.makentoshe.booruchan.screen.splash.viewmodel.SplashScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar

@Composable
internal fun SplashScreenUi(
    splashScreenState: SplashScreenState,
    viewModelEvent: (SplashScreenEvent) -> Unit,
) {
    SplashScreenLoading()
}

@Composable
private fun SplashScreenLoading() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    IndeterminateProgressBar()

    Text("SplashScreen")
}
