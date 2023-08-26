package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun SourceScreenUi(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = Scaffold(
    backgroundColor = BooruchanTheme.colors.background,
    topBar = {
        SourceScreenTopbar(screenState = screenState, screenEvent = screenEvent)
    },
    content = { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SourceScreenContent(screenState = screenState, screenEvent = screenEvent)
        }
    }
)
