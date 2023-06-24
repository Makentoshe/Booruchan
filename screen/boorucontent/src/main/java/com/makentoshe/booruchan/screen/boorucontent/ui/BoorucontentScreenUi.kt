package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState

@Composable
internal fun BoorucontentScreenUi(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        topBar = {
            BoorucontentTopBar(sheetState = sheetState, screenState = screenState, screenEvent = screenEvent, scaffoldState = scaffoldState)
        },
        sheetContent = {
            SearchBottomSheetContent(screenState = screenState, screenEvent = screenEvent)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                BoorucontentContent(screenState = screenState)
            }
        }
    )
}
