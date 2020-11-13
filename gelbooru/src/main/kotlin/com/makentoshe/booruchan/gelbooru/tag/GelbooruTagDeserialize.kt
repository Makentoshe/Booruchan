package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.core.tag.deserialize.TagDeserialize

typealias XmlGelbooruTagDeserialize = GelbooruTagDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagDeserialize = GelbooruTagDeserialize<JsonGelbooruTag>

data class GelbooruTagDeserialize<out Tag : GelbooruTag>(
    override val tag: Tag
) : TagDeserialize<Tag>
