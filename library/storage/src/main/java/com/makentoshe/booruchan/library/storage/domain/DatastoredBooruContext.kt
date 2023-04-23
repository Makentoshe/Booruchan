package com.makentoshe.booruchan.library.storage.domain

@kotlinx.serialization.Serializable
data class DatastoredBooruContext(
    val title: String,
    val system: String,
    val host: String,
)