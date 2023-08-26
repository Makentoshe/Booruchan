@file:OptIn(ExperimentalMaterialApi::class)

package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.foundation.PrimaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun SourceScreenUi(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = BackdropScaffold(
    backLayerBackgroundColor = BooruchanTheme.colors.background,
    frontLayerBackgroundColor = BooruchanTheme.colors.background,
    frontLayerShape = BooruchanTheme.shapes.backdrop,
    gesturesEnabled = false,
    appBar = {
        BooruchanTheme.colors
        SourceScreenTopbar(screenState = screenState, screenEvent = screenEvent)
    },
    frontLayerContent = {
        SourceScreenContent(screenState = screenState, screenEvent = screenEvent)
    },
    backLayerContent = {
        PrimaryText(text = "SASDASDAFSDFSDFSF")
    }
)
