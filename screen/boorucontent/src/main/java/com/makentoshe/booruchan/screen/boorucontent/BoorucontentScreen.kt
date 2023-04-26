package com.makentoshe.booruchan.screen.boorucontent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.BoorucontentScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentViewModel

@Composable
fun BoorucontentScreen(navigator: BoorucontentScreenNavigator, booruContextUrl: String) {
    screenLogInfo(Screen.Boorucontent, "OnCreateCompose: $booruContextUrl")

    // initialize viewmodel and state
    val viewModel = hiltViewModel<BoorucontentViewModel>()
    val boorucontentState by viewModel.stateFlow.collectAsState()

    // intialize viewmodel with screen argument
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(BoorucontentEvent.Initialize(booruContextUrl))
    }

    Text("Boorucontent screen: $booruContextUrl")
}
