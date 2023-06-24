package com.makentoshe.booruchan.core

import java.io.Serializable

/** Describes entity title or body */
interface Text: Serializable {
    val text: String
}

/** [Text] object constructor */
fun text(text: String) = object: Text {

    override val text = text

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Text

        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}