package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.foundation.ArrowBackIcon
import com.makentoshe.library.uikit.foundation.MagnifyIcon
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun SourceScreenTopbar(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = Column(modifier = Modifier) {
    TopAppBar(
        backgroundColor = BooruchanTheme.colors.background,
        title = {
            TopbarTitle(screenState = screenState, screenEvent = screenEvent)
        },
        navigationIcon = {
            TopbarNavigationIcon(screenState = screenState, screenEvent = screenEvent)
        },
        actions = { TopbarActionIcon(screenState = screenState, screenEvent = screenEvent) }
    )

    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = BooruchanTheme.colors.separator,
    )
}

@Composable
private fun TopbarNavigationIcon(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = Box(
    modifier = Modifier.size(48.dp).clickable { screenEvent(SourceScreenEvent.NavigationBack) },
    contentAlignment = Alignment.Center,
    content = { ArrowBackIcon() }
)

@Composable
private fun TopbarTitle(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = TitleText(
    text = screenState.sourceTitle,
    color = BooruchanTheme.colors.accent,
)

@Composable
private fun TopbarActionIcon(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = Box(
    modifier = Modifier.size(48.dp).clickable { },
    contentAlignment = Alignment.Center,
    content = { MagnifyIcon() }
)