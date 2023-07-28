package com.makentoshe.screen.boorulist.entity

import androidx.compose.runtime.Immutable

@Immutable
data class SourceUiState(
    val id: String,
    val title: String,
    val host: String,
    val health: SourceHealthUi,
)

enum class SourceHealthUi {
    Loading, Available, Unavailable,
}