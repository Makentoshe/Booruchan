package com.makentoshe.booruchan.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.splash.ui.SplashScreenUi
import com.makentoshe.booruchan.screen.splash.viewmodel.SplashScreenDestination
import com.makentoshe.booruchan.screen.splash.viewmodel.SplashScreenViewModel
import com.makentoshe.library.uikit.extensions.collectLatestInComposable

@Composable
fun SplashScreen(
    navigator: SplashScreenNavigator,
) {
    val viewModel = hiltViewModel<SplashScreenViewModel>()
    val splashState by viewModel.stateFlow.collectAsState()

    viewModel.navigationFlow.collectLatestInComposable { destination ->
        screenLogInfo(Screen.Home, "Navigation destination: $destination")
        when (destination) {
            is SplashScreenDestination.HomeScreen -> {
                navigator.navigateToHomeScreen()
            }
        }
    }

    SplashScreenUi(
        splashScreenState = splashState,
        viewModelEvent = viewModel::handleEvent,
    )

    screenLogInfo(Screen.Splash.toString(), "SplashScreen composable")
}