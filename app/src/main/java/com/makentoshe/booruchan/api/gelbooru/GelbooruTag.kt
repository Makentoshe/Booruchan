package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Tag

class GelbooruTag(
    override val raw: Map<String, String> = emptyMap(),
    override val title: String = raw["name"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val count: Int = raw["count"]?.toInt() ?: -1,
    override val ambiguous: Boolean = raw["ambiguous"]?.toBoolean() ?: false,
    override val type: Tag.Type = defineType(raw["type"]?.toInt())
) : Tag {

    companion object {
        private fun defineType(value: Int?) = when (value) {
            0 -> Tag.Type.GENERAL
            1 -> Tag.Type.ARTIST
            3 -> Tag.Type.COPYRIGHT
            4 -> Tag.Type.CHARACTER
            5 -> Tag.Type.METADATA
            else -> Tag.Type.UNDEFINED
        }
    }

}