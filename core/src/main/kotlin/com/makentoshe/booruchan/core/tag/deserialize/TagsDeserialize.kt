package com.makentoshe.booruchan.core.tag.deserialize

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.core.tag.Tag

interface TagsDeserialize<out T : Tag> {
    val deserializes: List<Result<TagDeserialize<T>>>
    val tags: List<T>
    val failures: List<EntityDeserializeException>
}