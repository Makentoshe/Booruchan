package com.makentoshe.booruchan.api

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

}