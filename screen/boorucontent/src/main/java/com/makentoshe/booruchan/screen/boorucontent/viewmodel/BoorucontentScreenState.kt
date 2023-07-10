package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import androidx.paging.PagingData
import com.makentoshe.booruchan.screen.boorucontent.domain.AutoCompleteTagUi
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class BoorucontentScreenState(
    val toolbarState: BoorucontentToolbarState,
    val bottomSheetState: BoorucontentBottomSheetState,
    val pagerFlow: Flow<PagingData<BooruPreviewPostUi>>,
) {
    companion object {
        val InitialState = BoorucontentScreenState(
            toolbarState = BoorucontentToolbarState.Loading,
            pagerFlow =  flowOf(),
            bottomSheetState = BoorucontentBottomSheetState(
                queryHint = "",
            ),
        )
    }
}

sealed interface  BoorucontentToolbarState {
    object Loading : BoorucontentToolbarState

    data class Content(val title: String) : BoorucontentToolbarState

    data class Error(val message: String) : BoorucontentToolbarState
}

data class BoorucontentBottomSheetState(
    val queryHint: String = "",
    val queryAutocomplete: List<AutoCompleteTagUi> = emptyList(),
    val queryTags: Set<SearchTagUi> = emptySet(),
)
