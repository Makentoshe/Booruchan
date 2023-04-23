package com.makentoshe.booruchan.feature.boorulist.domain

@kotlinx.serialization.Serializable
data class DatastoredBooruContext(
    val title: String,
    val system: String,
    val host: String,
)