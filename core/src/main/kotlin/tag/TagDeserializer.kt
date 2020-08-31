package tag

import tag.entity.Tag

/** Deserializes a string to a single [Tag] instance*/
interface TagDeserializer<out T: Tag> {
    fun deserialize(string: String): T
}