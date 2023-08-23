package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState

@Composable
fun SourceScreenUi(
    screenState: SourceScreenState,
) {
    when (val contentState = screenState.contentState) {
        ContentState.Loading -> {
            Text("Loading")
        }
        is ContentState.Failure -> {
            Text("Failure: ${contentState.string}")
        }
        is ContentState.Success -> {
            SourceSpannedVerticalGrid(
                contentState = contentState,
            )
        }
    }
}
