package tag.deserialize

import deserialize.EntityDeserializeException
import tag.Tag

interface TagsDeserialize<out T : Tag> {
    val deserializes: List<Result<TagDeserialize<T>>>
    val tags: List<T>
    val failures: List<EntityDeserializeException>
}