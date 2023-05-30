package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.RecyclerViewVerticalSpannedGrid
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model.BooruPostPagingDataAdapter
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout.BoorucontentErrorLayout
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout.BoorucontentLoadingLayout
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun BoorucontentContent(state: BoorucontentState) {
    val coroutineScope = rememberCoroutineScope()
    var pagingAdapter by rememberPagingAdapter()
    var loadStates by rememberPagingAdapterStates()

    if (loadStates.refresh is LoadState.Loading) {
        BoorucontentLoadingLayout()
    }

    if (loadStates.refresh is LoadState.Error) {
        BoorucontentErrorLayout(
            title = "There is a ###### error",
            description = "Maybe it is a network error, or something else. The message will be appeared here",
            button = "Retry",
            onClick = { pagingAdapter.retry() },
        )
    }

    RecyclerViewVerticalSpannedGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)
    ) { adapter ->
        // pass adapter to composable
        pagingAdapter = adapter

        // submit adapter states
        coroutineScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates = it }
        }

        // submit adapter data
        coroutineScope.launch {
            state.pagerFlow.collectLatest { adapter.submitData(it) }
        }
    }

    screenLogInfo(Screen.Boorucontent, "Adapter state change: $loadStates")
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

@Composable
private fun rememberPagingAdapter() = remember {
    mutableStateOf(BooruPostPagingDataAdapter())
}