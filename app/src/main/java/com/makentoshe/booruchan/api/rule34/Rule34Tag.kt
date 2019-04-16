package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.api.component.tag.Type

class Rule34Tag(
    override val raw: Map<String, String> = emptyMap(),
    override val title: String = raw["name"] ?: raw["value"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val count: Int = raw["count"]?.toInt() ?: -1,
    override val ambiguous: Boolean = raw["ambiguous"]?.toBoolean() ?: false,
    override val type: Type = Type.define(raw["type"]?.toInt())
) : Tag