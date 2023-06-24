package com.makentoshe.booruchan.danbooru.tag.deserialize

import com.makentoshe.booruchan.core.tag.deserialize.TagDeserialize
import com.makentoshe.booruchan.danbooru.tag.DanbooruTag
import com.makentoshe.booruchan.danbooru.tag.JsonDanbooruTag
import com.makentoshe.booruchan.danbooru.tag.XmlDanbooruTag

typealias XmlDanbooruTagDeserialize = DanbooruTagDeserialize<XmlDanbooruTag>
typealias JsonDanbooruTagDeserialize = DanbooruTagDeserialize<JsonDanbooruTag>

data class DanbooruTagDeserialize<out Tag : DanbooruTag>(
    override val tag: Tag, override val raw: String
) : TagDeserialize<Tag>
