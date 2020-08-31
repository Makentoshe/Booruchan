package tag

import tag.entity.Tag
import tag.entity.Tags

interface TagsDeserializer<T: Tag> {
    fun deserialize(string: String): Tags<T>
}

