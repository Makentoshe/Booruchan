package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import androidx.paging.PagingData
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class BoorucontentState(
    val toolbarState: BoorucontentToolbarState,
    val pagerFlow: Flow<PagingData<BooruPreviewPostUi>>,
) {
    companion object {
        val InitialState = BoorucontentState(
            toolbarState = BoorucontentToolbarState.Loading,
            pagerFlow =  flowOf(),
        )
    }
}

sealed interface  BoorucontentToolbarState {
    object Loading : BoorucontentToolbarState

    data class Content(val title: String) : BoorucontentToolbarState

    data class Error(val message: String) : BoorucontentToolbarState
}
