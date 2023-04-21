package com.makentoshe.screen.boorulist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.screen.boorulist.ui.BoorulistScreenUi

@Composable
fun BoorulistScreen(
    navigator: BoorulistScreenNavigator,
) {
    val viewModel = hiltViewModel<BoorulistViewModel>()
    val boorulistState by viewModel.stateFlow.collectAsState()

    BoorulistScreenUi(
        state = boorulistState,
        navigator = navigator,
        viewModelEvent = viewModel::handleEvent,
    )
}
