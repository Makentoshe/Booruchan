package com.makentoshe.booruchan.feature.serialization

@kotlinx.serialization.Serializable
data class SerializedBooruContext(
    val title: String,
    val system: String,
    val host: String,
)