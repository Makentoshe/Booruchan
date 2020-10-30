package com.makentoshe.booruchan.core.post

import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.text

interface Tags {
    val tags: Set<Text>

    companion object {
        val EMPTY = object : Tags {
            override val tags = emptySet<Text>()
        }
    }
}

/** This field used only for encoding to the url format and should not be used as default toString() output */
val Tags.string: String
    get() = tags.joinToString("%20") { it.text }

fun tagsFromText(set: Set<Text>) = object : Tags {
    override val tags = set
}

fun tagsFromString(set: Set<String>) = object : Tags {
    override val tags: Set<Text> = set.map(::text).toSet()
}
