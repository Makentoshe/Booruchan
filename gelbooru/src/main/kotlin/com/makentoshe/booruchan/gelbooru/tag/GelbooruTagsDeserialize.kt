package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize

typealias XmlGelbooruTagsDeserialize = GelbooruTagsDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagsDeserialize = GelbooruTagsDeserialize<JsonGelbooruTag>

data class GelbooruTagsDeserialize<out Tag : GelbooruTag>(
    override val deserializes: List<Result<GelbooruTagDeserialize<Tag>>>
) : TagsDeserialize<Tag> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val tags = deserializes.mapNotNull { it.getOrNull()?.tag }
}
