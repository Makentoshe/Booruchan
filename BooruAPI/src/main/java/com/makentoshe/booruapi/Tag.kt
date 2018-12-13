package com.makentoshe.booruapi

import java.io.Serializable

data class Tag(val raw: Map<String, String>): Serializable {

    constructor(
        name: String,
        id: Int = -1,
        count: Int = -1,
        ambiguous: Boolean? = null,
        type: Type = Type.UNDEFINED
    ): this(HashMap<String,String>().apply {
        put("name", name)
        put("id", id.toString())
        put("count", count.toString())
        put("ambiguous", ambiguous.toString())
        put("type", type.name.toLowerCase())
    })

    enum class Type {
        GENERAL, ARTIST, UNDEFINED, COPYRIGHT, CHARACTER, METADATA
    }

    var id: Int = -1
    lateinit var name: String
    var count: Int = -1
    var ambiguous: Boolean? = null
    var type = Type.UNDEFINED

    init {
        for (entry in raw.entries) {
            when (entry.key) {
                "id" -> {
                    id = entry.value.toInt()
                }
                "name" -> {
                    name = entry.value
                }
                "tag" -> {
                    name = entry.value
                }
                "count" -> {
                    count = entry.value.toInt()
                }
                "type" -> {
                    type = defineType(entry.value)
                }
                "ambiguous" -> {
                    ambiguous = defineAmbiguous(entry.value)
                }
            }
        }
    }

    private fun defineType(value: Int) = when(value) {
        0 -> Type.GENERAL
        1 -> Type.ARTIST
        3 -> Type.COPYRIGHT
        4 -> Type.CHARACTER
        5 -> Type.METADATA
        else -> Type.UNDEFINED
    }

    private fun defineType(value: String): Type = when(value) {
        Type.GENERAL.name.toLowerCase() -> Type.GENERAL
        Type.ARTIST.name.toLowerCase() -> Type.ARTIST
        Type.COPYRIGHT.name.toLowerCase() -> Type.COPYRIGHT
        Type.CHARACTER.name.toLowerCase() -> Type.CHARACTER
        Type.METADATA.name.toLowerCase() -> Type.METADATA
        "tag" -> Type.GENERAL
        else -> {
            try {
                defineType(value.toInt())
            } catch (e: NumberFormatException) {
                Type.UNDEFINED
            }
        }
    }

    private fun defineAmbiguous(value: String): Boolean? {
        if (value == "null") return null
        return try {
            value.toInt() == 1
        } catch (e: NumberFormatException) {
            value.toBoolean()
        }
    }
}
