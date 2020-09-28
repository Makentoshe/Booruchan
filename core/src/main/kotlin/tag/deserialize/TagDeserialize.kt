package tag.deserialize

import tag.Tag

interface TagDeserialize<out T : Tag> {
    val tag: T
}