package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.api.Tag.Companion.defineTagType

class GelbooruTag(
    override val raw: Map<String, String> = emptyMap(),
    override val title: String = raw["name"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val count: Int = raw["count"]?.toInt() ?: -1,
    override val ambiguous: Boolean = raw["ambiguous"]?.toBoolean() ?: false,
    override val type: Tag.Type = defineTagType(raw["type"]?.toInt())
) : Tag