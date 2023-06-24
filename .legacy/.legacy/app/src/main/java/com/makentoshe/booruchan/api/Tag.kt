package com.makentoshe.booruchan.api

import androidx.core.text.isDigitsOnly
import java.io.Serializable

interface Tag : Serializable {
    val raw: Map<String, String>
    val id: Long
    val title: String
    val count: Int
    val ambiguous: Boolean
    val type: Type

    enum class Type {
        GENERAL, ARTIST, UNDEFINED, COPYRIGHT, CHARACTER, METADATA
    }

    companion object {
        fun create(title: String) = object : Tag {
            override val title = title
            override val raw: Map<String, String> = emptyMap()
            override val id: Long = -1L
            override val count: Int = -1
            override val ambiguous: Boolean = false
            override val type: Tag.Type = Tag.Type.UNDEFINED
        }

        fun defineTagType(value: Int?) = when (value) {
            0 -> Tag.Type.GENERAL
            1 -> Tag.Type.ARTIST
            3 -> Tag.Type.COPYRIGHT
            4 -> Tag.Type.CHARACTER
            5 -> Tag.Type.METADATA
            else -> Tag.Type.UNDEFINED
        }

        fun defineTagType(value: String?) = when (value) {
            "general" -> Tag.Type.GENERAL
            "artist" -> Tag.Type.ARTIST
            "copyright" -> Tag.Type.COPYRIGHT
            "character" -> Tag.Type.CHARACTER
            "metadata" -> Tag.Type.METADATA
            else -> if (value != null && value.isDigitsOnly()) {
                defineTagType(value.toInt())
            } else {
                Tag.Type.UNDEFINED
            }
        }
    }
}