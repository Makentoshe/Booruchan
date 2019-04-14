package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.Tag

class Rule34Tag(
    override val raw: Map<String, String> = emptyMap(),
    override val title: String = raw["name"] ?: raw["label"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val count: Int = raw["count"]?.toInt() ?: -1,
    override val ambiguous: Boolean = raw["ambiguous"]?.toBoolean() ?: false,
    override val type: Tag.Type = Tag.defineTagType(raw["type"]?.toInt())
) : Tag