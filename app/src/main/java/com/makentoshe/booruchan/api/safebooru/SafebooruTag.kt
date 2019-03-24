package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Tag

class SafebooruTag(
    override val raw: Map<String, String> = emptyMap(),
    override val title: String = raw["name"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val count: Int = raw["count"]?.toInt() ?: -1,
    override val ambiguous: Boolean = raw["ambiguous"]?.toBoolean() ?: false,
    override val type: Tag.Type = defineType(raw["type"])
) : Tag {

    companion object {
        fun defineType(value: String?) = when (value) {
            "general" -> Tag.Type.GENERAL
            "artist" -> Tag.Type.ARTIST
            "copyright" -> Tag.Type.COPYRIGHT
            "character" -> Tag.Type.CHARACTER
            "metadata" -> Tag.Type.METADATA
            else -> Tag.Type.UNDEFINED
        }
    }

}