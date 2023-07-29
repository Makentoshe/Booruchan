package com.makentoshe.screen.boorulist.viewmodel

/** Destinations that can be triggered */
sealed interface HomeScreenDestination {

    data class SourceDestination(val sourceId: String): HomeScreenDestination
}