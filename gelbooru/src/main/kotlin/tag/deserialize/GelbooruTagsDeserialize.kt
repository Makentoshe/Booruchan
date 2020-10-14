package tag.deserialize

import deserialize.EntityDeserializeException
import tag.GelbooruTag
import tag.JsonGelbooruTag
import tag.XmlGelbooruTag

typealias XmlGelbooruTagsDeserialize = GelbooruTagsDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagsDeserialize = GelbooruTagsDeserialize<JsonGelbooruTag>

data class GelbooruTagsDeserialize<out Tag : GelbooruTag>(
    override val deserializes: List<Result<GelbooruTagDeserialize<Tag>>>
) : TagsDeserialize<Tag> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val tags = deserializes.mapNotNull { it.getOrNull()?.tag }
}
