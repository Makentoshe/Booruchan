package com.makentoshe.booruchan.api.component.post

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.api.component.tag.TagFactory
import java.io.Serializable

class PostTagsParser(private val tagFactory: TagFactory<out Tag>) : Parser<Array<Tag>>, Serializable {

    override fun parse(data: String): Array<Tag> {
        if (data.isBlank()) return arrayOf()
        val stags = data.split(" ").toTypedArray()
        return Array(stags.size) { tagFactory.build("name" to stags[it], "title" to stags[it]) }
    }
}