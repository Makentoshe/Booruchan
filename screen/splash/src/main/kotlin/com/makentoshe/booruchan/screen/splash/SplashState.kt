package com.makentoshe.booruchan.screen.splash

sealed interface SplashState {
    object Loading : SplashState

    data class Content(
        val booruItems: List<BooruItemState>,
    ) : SplashState
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