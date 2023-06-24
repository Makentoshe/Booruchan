package com.makentoshe.booruchan.core.tag

import com.makentoshe.booruchan.core.Text
import java.io.Serializable

interface Tag : TagId, Text, Serializable {
    /** Mandatory param: tag type */
    val type: Type
}

/** [Tag] object constructor */
fun tag(id: Int, type: Type, text: String) = object : Tag {
    override val type = type
    override val tagId = id
    override val text = text

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (type != other.type) return false
        if (tagId != other.tagId) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + tagId
        result = 31 * result + text.hashCode()
        return result
    }
}
