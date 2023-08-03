package com.makentoshe.booruchan.screen.source.viewmodel

import javax.annotation.concurrent.Immutable

data class SourceScreenState(
    val contentState: ContentState,
) {
    companion object {
        val InitialState = SourceScreenState(
            contentState = ContentState.Loading,
        )
    }
}

@Immutable
sealed interface ContentState {
    object Loading: ContentState

    data class Success(val string: String): ContentState

    data class Failure(val string: String): ContentState
}