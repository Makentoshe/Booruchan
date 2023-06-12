package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.resources.R
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentToolbarState
import com.makentoshe.library.uikit.foundation.ArrowBackIcon
import com.makentoshe.library.uikit.foundation.MagnifyIcon
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
internal fun BoorucontentTopBar(
    sheetState: SheetState,
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) = Column(modifier = Modifier.fillMaxWidth().background(BooruchanTheme.colors.background)) {
    BoorucontentTopBarContent(screenState = screenState, screenEvent = screenEvent, sheetState = sheetState)
    Divider(color = BooruchanTheme.colors.separator, thickness = 1.dp)
}

@Composable
private fun BoorucontentTopBarContent(
    sheetState: SheetState,
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth().height(56.dp)
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.size(56.dp).clickable {
            screenEvent(BoorucontentScreenEvent.NavigationBack)
        },
        contentAlignment = Alignment.Center,
    ) {
        ArrowBackIcon()
    }

    Row(
        modifier = Modifier.fillMaxSize().padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            when (val toolbarState = screenState.toolbarState) {
                is BoorucontentToolbarState.Content -> {
                    TitleText(text = toolbarState.title)
                }
                is BoorucontentToolbarState.Error -> {
                    TitleText(text = toolbarState.message)
                }
                BoorucontentToolbarState.Loading -> {
                    TitleText(text = stringResource(id = R.string.boorucontent_title_loading))
                }
            }
        }

        Box(
            modifier = Modifier.size(56.dp).clickable {
                coroutineScope.launch(Dispatchers.IO) { sheetState.expand() }
            },
            contentAlignment = Alignment.Center,
        ) {
            MagnifyIcon()
        }
    }
}
