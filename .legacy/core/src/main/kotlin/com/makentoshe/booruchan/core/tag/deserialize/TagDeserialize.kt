package com.makentoshe.booruchan.core.tag.deserialize

import com.makentoshe.booruchan.core.tag.Tag

interface TagDeserialize<out T : Tag> {
    val tag: T

    /** An object representation in string (json, xml) */
    val raw: String
}