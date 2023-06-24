package com.makentoshe.booruchan.core.post.deserialize

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.core.post.Post

interface PostsDeserialize<out P : Post> {

    /** A list of objects representation in string (json, xml) */
    val rawValue: String

    /** The first raw objects deserializes from [rawValue] */
    val deserializes: List<Result<PostDeserialize<P>>>

    /** Successfully deserialized posts filtered from [deserializes] */
    val posts: List<P>

    /** A list of exceptions with raw values filtered from [deserializes] */
    val failures: List<EntityDeserializeException>
}