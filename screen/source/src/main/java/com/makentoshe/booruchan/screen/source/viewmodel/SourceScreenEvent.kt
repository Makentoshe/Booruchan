package com.makentoshe.booruchan.screen.source.viewmodel

sealed interface SourceScreenEvent {

    data class Initialize(val sourceId: String): SourceScreenEvent

    object NavigationBack: SourceScreenEvent

}