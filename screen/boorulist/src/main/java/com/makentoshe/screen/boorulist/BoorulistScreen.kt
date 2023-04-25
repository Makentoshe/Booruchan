package com.makentoshe.screen.boorulist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.screen.boorulist.ui.BoorulistScreenUi
import com.makentoshe.screen.boorulist.viewmodel.BoorulistDestination
import com.makentoshe.screen.boorulist.viewmodel.BoorulistViewModel

@Composable
fun BoorulistScreen(navigator: BoorulistScreenNavigator) {
    screenLogInfo(Screen.Boorulist, "OnComposeCreate")

    val viewModel = hiltViewModel<BoorulistViewModel>()
    val boorulistState by viewModel.stateFlow.collectAsState()
    val navigationState by viewModel.navigationFlow.collectAsState()

    when(navigationState) {
        BoorulistDestination.NoneDestination -> Unit // Do nothing
        is BoorulistDestination.BoorucontentDestination -> {
            navigator.navigateToBoorucontentScreen()
        }
    }

    BoorulistScreenUi(
        state = boorulistState,
        event = viewModel::handleEvent,
    )
}
