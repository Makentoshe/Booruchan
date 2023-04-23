package com.makentoshe.booruchan.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.splash.ui.SplashScreenUi

@Composable
fun SplashScreen(
    navigator: SplashScreenNavigator,
) {

    val viewModel = hiltViewModel<SplashScreenViewModel>()
    val splashState by viewModel.stateFlow.collectAsState()

    SplashScreenUi(
        splashState = splashState,
        navigator = navigator,
        viewModelEvent = viewModel::handleEvent,
    )
}