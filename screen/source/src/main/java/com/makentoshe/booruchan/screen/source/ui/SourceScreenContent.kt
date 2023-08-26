package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenEvent
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.PrimaryTextBold
import com.makentoshe.library.uikit.foundation.SecondaryText


@Composable
internal fun SourceScreenContent(
    screenState: SourceScreenState,
    screenEvent: (SourceScreenEvent) -> Unit,
) = when (val contentState = screenState.contentState) {
    is ContentState.Loading -> {
        SourceScreenContentLoading()
    }
    is ContentState.Failure -> {
        SourceScreenContentFailure(title = contentState.title, description = contentState.description)
    }
    is ContentState.Success -> {
        SourceScreenContentSuccess(screenState = screenState, contentState = contentState)
    }
}

@Composable
private fun SourceScreenContentLoading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    content = { IndeterminateProgressBar() }
)

@Composable
private fun SourceScreenContentFailure(
    title: String,
    description: String,
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    Column(
        modifier = Modifier.fillMaxWidth().height(128.dp).padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryTextBold(text = title, textAlign = TextAlign.Center)
        SecondaryText(text = description, textAlign = TextAlign.Center)
        TextButton(onClick = {}) {
            SecondaryText(text = "Retry")
        }
    }
}

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
            SourceScreenContentFailure("", description = refreshState.error.toString())
        }
        else -> {
            SourceLazyVerticalStaggeredGrid(
                screenState = screenState,
                contentState = contentState,
            )
        }
    }
}

