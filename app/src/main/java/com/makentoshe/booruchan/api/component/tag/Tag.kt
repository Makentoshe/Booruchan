package com.makentoshe.booruchan.api.component.tag

import java.io.Serializable

interface Tag : Serializable {
    val raw: Map<String, String>
    val id: Long
    val title: String
    val count: Int
    val ambiguous: Boolean
    val type: Type

    companion object {
        fun create(title: String) = object : Tag {
            override val title = title
            override val raw: Map<String, String> = emptyMap()
            override val id: Long = -1L
            override val count: Int = -1
            override val ambiguous: Boolean = false
            override val type: Type =
                Type.UNDEFINED
        }
    }
}

