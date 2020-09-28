package tag.deserialize

import deserialize.EntityDeserializeException
import tag.DanbooruTag
import tag.JsonDanbooruTag
import tag.XmlDanbooruTag

typealias XmlDanbooruTagsDeserialize = DanbooruTagsDeserialize<XmlDanbooruTag>
typealias JsonDanbooruTagsDeserialize = DanbooruTagsDeserialize<JsonDanbooruTag>

data class DanbooruTagsDeserialize<out Tag : DanbooruTag>(
    override val deserializes: List<Result<DanbooruTagDeserialize<Tag>>>
) : TagsDeserialize<Tag> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val tags = deserializes.mapNotNull { it.getOrNull()?.tag }
}
