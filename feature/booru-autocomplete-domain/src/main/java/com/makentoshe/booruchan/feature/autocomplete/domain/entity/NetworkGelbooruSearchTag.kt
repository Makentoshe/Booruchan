package com.makentoshe.booruchan.feature.autocomplete.domain.entity

@kotlinx.serialization.Serializable
data class NetworkGelbooruSearchTag(
    override val value: String,
    override val label: String,
    val type: String,
    val postCount: Int,
    val category: String,
): NetworkSearchTag
