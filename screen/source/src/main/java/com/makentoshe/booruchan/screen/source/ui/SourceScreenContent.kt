package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme


@Composable
internal fun SourceScreenContent(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = when (val contentState = screenState.contentState) {
    is ContentState.Loading -> SourceScreenContentLoading()
    is ContentState.Failure -> SourceScreenContentFailure(message = contentState.string)
    is ContentState.Success -> SourceScreenContentSuccess(screenState = screenState, contentState = contentState)
}

@Composable
private fun SourceScreenContentLoading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    content = { IndeterminateProgressBar() }
)

@Composable
private fun SourceScreenContentFailure(message: String) = Column(
    modifier = Modifier.fillMaxSize(),
    content = { SecondaryText(text = message, color = BooruchanTheme.colors.dimmed) }
)

@Composable
private fun SourceScreenContentSuccess(
    screenState: SourceScreenState,
    contentState: ContentState.Success,
) {
    val previewPostItems = contentState.pagerFlow.collectAsLazyPagingItems()
    val refreshState = previewPostItems.loadState.refresh
    val prependState = previewPostItems.loadState.prepend

    when {
        refreshState is LoadState.Loading && !prependState.endOfPaginationReached -> {
            SourceScreenContentLoading()
        }
        refreshState is LoadState.Error -> {
            SourceScreenContentFailure(message = refreshState.error.toString())
        }
        else -> {
            SourceLazyVerticalStaggeredGrid(
                screenState = screenState,
                contentState = contentState,
            )
        }
    }
}

