package com.makentoshe.booruchan.danbooru.tag.deserialize

import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.danbooru.tag.DanbooruTag
import com.makentoshe.booruchan.danbooru.tag.JsonDanbooruTag
import com.makentoshe.booruchan.danbooru.tag.XmlDanbooruTag

typealias XmlDanbooruTagsDeserialize = DanbooruTagsDeserialize<XmlDanbooruTag>
typealias JsonDanbooruTagsDeserialize = DanbooruTagsDeserialize<JsonDanbooruTag>

data class DanbooruTagsDeserialize<out Tag : DanbooruTag>(
    override val deserializes: List<Result<DanbooruTagDeserialize<Tag>>>
) : TagsDeserialize<Tag> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val tags = deserializes.mapNotNull { it.getOrNull()?.tag }
}
