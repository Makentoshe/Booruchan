package com.makentoshe.booruchan.feature.autocomplete.domain.entity

@kotlinx.serialization.Serializable
data class NetworkSafebooruSearchTag(
    override val value: String,
    override val label: String,
): NetworkSearchTag
