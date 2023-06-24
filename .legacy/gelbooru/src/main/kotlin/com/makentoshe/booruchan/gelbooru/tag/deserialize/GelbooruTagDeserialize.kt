package com.makentoshe.booruchan.gelbooru.tag.deserialize

import com.makentoshe.booruchan.core.tag.deserialize.TagDeserialize
import com.makentoshe.booruchan.gelbooru.tag.GelbooruTag
import com.makentoshe.booruchan.gelbooru.tag.JsonGelbooruTag
import com.makentoshe.booruchan.gelbooru.tag.XmlGelbooruTag

typealias XmlGelbooruTagDeserialize = GelbooruTagDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagDeserialize = GelbooruTagDeserialize<JsonGelbooruTag>

data class GelbooruTagDeserialize<out Tag : GelbooruTag>(
    override val tag: Tag, override val raw: String
) : TagDeserialize<Tag>
