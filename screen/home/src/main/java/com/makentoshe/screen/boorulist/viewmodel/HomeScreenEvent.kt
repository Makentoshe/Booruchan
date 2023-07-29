package com.makentoshe.screen.boorulist.viewmodel

sealed interface HomeScreenEvent {
    data class NavigationSource(val sourceId: String): HomeScreenEvent
    object RefreshPlugins: HomeScreenEvent
}
