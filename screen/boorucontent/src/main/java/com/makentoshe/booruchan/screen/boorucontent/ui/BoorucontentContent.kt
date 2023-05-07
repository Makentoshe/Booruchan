package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.RecyclerViewVerticalSpannedGrid
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout.BoorucontentErrorLayout
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout.BoorucontentLoadingLayout
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun BoorucontentContent(state: BoorucontentState) {
    val coroutineScope = rememberCoroutineScope()
    var loadStates by rememberPagingAdapterStates()
    println(loadStates)

    if (loadStates.refresh is LoadState.Loading) {
        BoorucontentLoadingLayout()
    }
    if (loadStates.refresh is LoadState.Error) {
        BoorucontentErrorLayout()
    }

    RecyclerViewVerticalSpannedGrid { adapter ->

        coroutineScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates = it }
        }

        // submit adapter data
        coroutineScope.launch {
            state.pagerFlow.collectLatest { adapter.submitData(it) }
        }
    }
}

@Composable
private fun rememberPagingAdapterStates() = remember {
    mutableStateOf(
        CombinedLoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
            source = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
            mediator = null,
        )
    )
}