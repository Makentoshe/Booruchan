package com.makentoshe.booruchan.screen.boorucontent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.BoorucontentScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.boorucontent.ui.BoorucontentScreenUi
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentDestination
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentViewModel
import com.makentoshe.library.uikit.extensions.collectLatestInComposable

@Composable
fun BoorucontentScreen(
    navigator: BoorucontentScreenNavigator,
    booruContextUrl: String,
) {
    // initialize viewmodel and state
    val viewModel = hiltViewModel<BoorucontentViewModel>()
    val boorucontentState by viewModel.stateFlow.collectAsState()

    // intialize viewmodel with screen argument
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(BoorucontentScreenEvent.Initialize(booruContextUrl))
    }

    // initializa navigation
    viewModel.navigationFlow.collectLatestInComposable { destination ->
        screenLogInfo(Screen.Boorulist, "Navigation destination: $destination")
        when (destination) {
            BoorucontentDestination.BackDestination -> navigator.back()
        }
    }

    // initialize user interface
    BoorucontentScreenUi(
        screenState = boorucontentState,
        screenEvent = viewModel::handleEvent,
    )

    screenLogInfo(Screen.Boorucontent, "OnCreateCompose: $booruContextUrl")
}
