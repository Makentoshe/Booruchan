package com.makentoshe.booruchan.screen.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.SourceScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.source.ui.SourceScreenUi
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenDestination
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenViewModel
import com.makentoshe.library.uikit.extensions.collectLatestInComposable

@Composable
fun SourceScreen(
    navigator: SourceScreenNavigator,
    sourceId: String,
) {
    val viewModel = hiltViewModel<SourceScreenViewModel>()
    val screenState by viewModel.stateFlow.collectAsState()

    viewModel.navigationFlow.collectLatestInComposable { destination ->
        screenLogInfo(Screen.Source, "Navigation destination: $destination")
        when (destination) {
            SourceScreenDestination.BackDestination -> {
                navigator.back()
            }
        }
    }

    // intialize viewmodel with screen argument
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(SourceScreenEvent.Initialize(sourceId = sourceId))
    }

    SourceScreenUi(screenState = screenState, screenEvent = viewModel::handleEvent)

    screenLogInfo(Screen.Source, "SourceScreen composable")
}
