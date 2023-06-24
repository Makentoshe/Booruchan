package com.makentoshe.booruchan.feature.boorulist.domain

@kotlinx.serialization.Serializable
data class DatastoredBooruContext(
    val title: String,
    val system: String,
    val host: String,
    val settings: DatastoredBooruSettings,
)

@kotlinx.serialization.Serializable
data class DatastoredBooruSettings(
    val searchSettings: DatastoredBooruSearchSettings,
)

@kotlinx.serialization.Serializable
data class DatastoredBooruSearchSettings(
    val requestedPostsPerPageCount: Int,
    val initialPageNumber: Int,
    val hint: String,
)