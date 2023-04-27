package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentContentState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.PrimaryText

@Composable
internal fun BoorucontentContent(state: BoorucontentState) {
    when(val contentState = state.contentState) {
        BoorucontentContentState.Loading -> BoorucontentContentLoading()
        is BoorucontentContentState.Error -> BoorucontentContentError(state = contentState)
        is BoorucontentContentState.Content -> BoorucontentContentContent(state = contentState)
    }
}

@Composable
private fun BoorucontentContentLoading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    content = { IndeterminateProgressBar() }
)

@Composable
private fun BoorucontentContentError(state: BoorucontentContentState) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    content = { PrimaryText(text = "TODO: Error layout") }
)

@Composable
private fun BoorucontentContentContent(state: BoorucontentContentState) = Column() {
    PrimaryText("TODO: Content layout")
}

