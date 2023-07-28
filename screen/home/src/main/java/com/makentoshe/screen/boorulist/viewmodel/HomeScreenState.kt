package com.makentoshe.screen.boorulist.viewmodel

import com.makentoshe.screen.boorulist.entity.SourceUiState

data class HomeScreenState(
    val pluginContent: HomeScreenPluginContent,
) {
    companion object {
        val InitialState = HomeScreenState(
            pluginContent = HomeScreenPluginContent.Loading,
        )
    }
}

sealed interface HomeScreenPluginContent {
    object Loading : HomeScreenPluginContent

    data class Content(
        val refreshing: Boolean,
        val sources: List<SourceUiState>,
    ) : HomeScreenPluginContent
}
