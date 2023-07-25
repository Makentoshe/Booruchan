package com.makentoshe.screen.boorulist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.HomeScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.library.uikit.extensions.collectLatestInComposable
import com.makentoshe.screen.boorulist.ui.HomeScreenUi
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(navigator: HomeScreenNavigator) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val boorulistState by viewModel.stateFlow.collectAsState()

    viewModel.navigationFlow.collectLatestInComposable { destination ->
//        screenLogInfo(Screen.Boorulist, "Navigation destination: $destination")
//        when (destination) {
//            is BoorulistDestination.BoorucontentDestination -> {
//                navigator.navigateToBoorucontentScreen(destination.booruSourceId)
//            }
//        }
    }

    HomeScreenUi(
        state = boorulistState,
        event = viewModel::handleEvent,
    )

    screenLogInfo(Screen.Home, "HomeScreen composable")
}
