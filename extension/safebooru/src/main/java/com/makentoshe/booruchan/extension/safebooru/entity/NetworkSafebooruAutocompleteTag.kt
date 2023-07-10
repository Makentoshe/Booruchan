package com.makentoshe.booruchan.extension.safebooru.entity

import com.makentoshe.booruchan.extension.entity.NetworkAutocompleteTag
import java.util.*

@kotlinx.serialization.Serializable
data class NetworkSafebooruAutocompleteTag(
    private val label: String,
    override val value: String,
) : NetworkAutocompleteTag {
    override val count: Int = label.split(" ").last().replace(Regex("\\D"), "").toInt()
    override val title: String = value.replace("_", " ")
}
