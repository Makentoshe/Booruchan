package com.makentoshe.booruchan.screen.splash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.splash.SplashEvent
import com.makentoshe.booruchan.screen.splash.SplashState

@Composable
internal fun SplashScreenUi(
    splashState: SplashState,
    navigator: SplashScreenNavigator,
    viewModelEvent: (SplashEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("SplashScreen")

        Button(onClick = {
            navigator.navigateToBoorulistScreen()
        }) {
            Text(text = "Boorulist screen")
        }
    }
}