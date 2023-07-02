package com.makentoshe.screen.boorulist.viewmodel

sealed interface BoorulistEvent {
    data class NavigateToBoorucontentScreen(val booruSourceIndex: Int): BoorulistEvent
}
