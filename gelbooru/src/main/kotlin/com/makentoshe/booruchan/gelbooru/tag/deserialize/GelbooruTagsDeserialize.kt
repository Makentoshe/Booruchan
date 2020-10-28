package com.makentoshe.booruchan.gelbooru.tag.deserialize

import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.gelbooru.tag.GelbooruTag
import com.makentoshe.booruchan.gelbooru.tag.JsonGelbooruTag
import com.makentoshe.booruchan.gelbooru.tag.XmlGelbooruTag

typealias XmlGelbooruTagsDeserialize = GelbooruTagsDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagsDeserialize = GelbooruTagsDeserialize<JsonGelbooruTag>

data class GelbooruTagsDeserialize<out Tag : GelbooruTag>(
    override val deserializes: List<Result<GelbooruTagDeserialize<Tag>>>
) : TagsDeserialize<Tag> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val tags = deserializes.mapNotNull { it.getOrNull()?.tag }
}
