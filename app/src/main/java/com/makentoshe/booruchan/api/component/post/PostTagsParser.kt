package com.makentoshe.booruchan.api.component.post

import com.makentoshe.booruchan.api.component.parser.Parser
import com.makentoshe.booruchan.api.Tag

class PostTagsParser(private val tagFactory: (String) -> Tag) :
    Parser<Array<Tag>> {
    override fun parse(data: String): Array<Tag> {
        if (data.isBlank()) return arrayOf()
        val stags = data.split(" ").toTypedArray()
        return Array(stags.size) { tagFactory(stags[it]) }
    }
}