package com.makentoshe.screen.boorulist

sealed interface BoorulistState {
    object Loading : BoorulistState

    data class Content(
        val booruItems: List<BooruItemState>,
    ) : BoorulistState
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