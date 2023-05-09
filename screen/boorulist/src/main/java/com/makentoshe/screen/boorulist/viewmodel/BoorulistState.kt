package com.makentoshe.screen.boorulist.viewmodel

data class BoorulistState(
    val content: BoorulistStateContent,
) {
    companion object {
        val InitialState = BoorulistState(
            content = BoorulistStateContent.Loading,
        )
    }
}

sealed interface BoorulistStateContent {
    object Loading : BoorulistStateContent {
        override fun toString() = "Loading"
    }

    data class Content(
        val booruItems: List<BooruItemState>,
    ) : BoorulistStateContent
}

data class BooruItemState(
    val title: String,
    val url: String,
    val health: BooruItemHealthState,
)

sealed interface BooruItemHealthState {
    object Loading : BooruItemHealthState
    object Ok : BooruItemHealthState
    object Error : BooruItemHealthState
}