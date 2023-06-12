package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun BoorucontentScreenUi(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        containerColor = BooruchanTheme.colors.background,
        topBar = {
            BoorucontentTopBar(
                screenState = screenState,
                sheetState = sheetState,
                screenEvent = screenEvent,
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            BoorucontentContent(screenState = screenState)
        }
    }

    if (sheetState.isVisible) {
        SearchBottomSheet(screenState = screenState, sheetState = sheetState)
    }
}
