package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalComposeUiApi::class)
internal fun BoorucontentScreenUi(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        topBar = {
            BoorucontentTopBar(screenState = screenState, screenEvent = screenEvent) {
                coroutineScope.launch(Dispatchers.IO) { scaffoldState.bottomSheetState.expand() }
            }
        },
        sheetContent = {
            SearchBottomSheetContent(screenState = screenState, screenEvent = screenEvent) {
                screenEvent(BoorucontentScreenEvent.Search)
                coroutineScope.launch(Dispatchers.IO) { scaffoldState.bottomSheetState.partialExpand() }
                keyboardController?.hide()
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                BoorucontentContent(screenState = screenState)
            }
        }
    )
}
