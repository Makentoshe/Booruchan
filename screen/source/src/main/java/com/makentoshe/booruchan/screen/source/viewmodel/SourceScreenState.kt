package com.makentoshe.booruchan.screen.source.viewmodel

import androidx.paging.PagingData
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import kotlinx.coroutines.flow.Flow
import javax.annotation.concurrent.Immutable

data class SourceScreenState(
    val sourceTitle: String,
    val contentState: ContentState,
) {
    companion object {
        val InitialState = SourceScreenState(
            sourceTitle = "",
            contentState = ContentState.Loading,
        )
    }
}

@Immutable
sealed interface ContentState {
    object Loading : ContentState

    data class Success(
        val pagerFlow: Flow<PagingData<PreviewPostUiState>>,
    ) : ContentState

    data class Failure(val string: String) : ContentState
}