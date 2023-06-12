package com.makentoshe.booruchan.screen.boorucontent.viewmodel

sealed interface BoorucontentScreenEvent {
    data class Initialize(val booruContextUrl: String): BoorucontentScreenEvent
    object NavigationBack: BoorucontentScreenEvent
}