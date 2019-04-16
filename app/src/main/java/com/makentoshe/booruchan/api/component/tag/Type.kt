package com.makentoshe.booruchan.api.component.tag

import androidx.core.text.isDigitsOnly

enum class Type {
    GENERAL, ARTIST, UNDEFINED, COPYRIGHT, CHARACTER, METADATA;

    companion object {
        fun define(value: String?) = when (value) {
            "general" -> GENERAL
            "artist" -> ARTIST
            "copyright" -> COPYRIGHT
            "character" -> CHARACTER
            "metadata" -> METADATA
            else -> if (value != null && value.isDigitsOnly()) {
                define(value.toInt())
            } else {
                UNDEFINED
            }
        }

        fun define(value: Int?) = when (value) {
            0 -> GENERAL
            1 -> ARTIST
            3 -> COPYRIGHT
            4 -> CHARACTER
            5 -> METADATA
            else -> UNDEFINED
        }
    }
}