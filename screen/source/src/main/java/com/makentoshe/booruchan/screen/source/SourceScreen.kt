package com.makentoshe.booruchan.screen.source

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.SourceScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import com.makentoshe.booruchan.screen.source.ui.SourceScreenUi
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenViewModel
import com.makentoshe.library.uikit.layout.spanned.SpanSize
import com.makentoshe.library.uikit.layout.spanned.SpannedVerticalGrid

@Composable
fun SourceScreen(
    navigator: SourceScreenNavigator,
    sourceId: String,
) {
    val viewModel = hiltViewModel<SourceScreenViewModel>()
    val screenState by viewModel.stateFlow.collectAsState()

    // intialize viewmodel with screen argument
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(SourceScreenEvent.Initialize(sourceId = sourceId))
    }

    SourceScreenUi(screenState = screenState, viewModel = viewModel)

    screenLogInfo(Screen.Source, "SourceScreen composable")
}
