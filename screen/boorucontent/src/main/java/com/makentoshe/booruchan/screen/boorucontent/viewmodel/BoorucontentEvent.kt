package com.makentoshe.booruchan.screen.boorucontent.viewmodel

sealed interface BoorucontentEvent {
    data class Initialize(val booruContextUrl: String): BoorucontentEvent

    object NavigationBack: BoorucontentEvent
}