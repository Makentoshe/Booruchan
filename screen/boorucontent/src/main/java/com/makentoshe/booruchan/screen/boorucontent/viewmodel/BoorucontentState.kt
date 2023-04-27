package com.makentoshe.booruchan.screen.boorucontent.viewmodel

data class BoorucontentState(
    val toolbarState: BoorucontentToolbarState,
    val contentState: BoorucontentContentState,
) {
    companion object {
        val InitialState = BoorucontentState(
            toolbarState = BoorucontentToolbarState.Loading,
            contentState = BoorucontentContentState.Loading,
        )
    }
}

sealed interface  BoorucontentToolbarState {
    object Loading : BoorucontentToolbarState

    data class Content(val title: String) : BoorucontentToolbarState

    data class Error(val message: String) : BoorucontentToolbarState
}

sealed interface  BoorucontentContentState {
    object Loading : BoorucontentContentState

    data class Content(val title: String) : BoorucontentContentState

    data class Error(val message: String) : BoorucontentContentState
}
