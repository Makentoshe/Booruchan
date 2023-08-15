package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.runtime.Composable
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenViewModel

@Composable
fun SourceScreenUi(
    screenState: SourceScreenState,
    viewModel: SourceScreenViewModel,
) {
    SourceSpannedVerticalGrid()
}
