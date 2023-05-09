package com.makentoshe.screen.boorulist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.library.uikit.extensions.collectLatestInComposable
import com.makentoshe.screen.boorulist.ui.BoorulistScreenUi
import com.makentoshe.screen.boorulist.viewmodel.BoorulistDestination
import com.makentoshe.screen.boorulist.viewmodel.BoorulistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun BoorulistScreen(navigator: BoorulistScreenNavigator) {
    val viewModel = hiltViewModel<BoorulistViewModel>()
    val boorulistState by viewModel.stateFlow.collectAsState()

    viewModel.navigationFlow.collectLatestInComposable { destination ->
        screenLogInfo(Screen.Boorulist, "Navigation destination: $destination")
        when (destination) {
            is BoorulistDestination.BoorucontentDestination -> {
                navigator.navigateToBoorucontentScreen(destination.booruContextUrl)
            }
        }
    }

    BoorulistScreenUi(
        state = boorulistState,
        event = viewModel::handleEvent,
    )

    screenLogInfo(Screen.Boorulist, "OnComposeCreate: boorulistState=$boorulistState")
}
