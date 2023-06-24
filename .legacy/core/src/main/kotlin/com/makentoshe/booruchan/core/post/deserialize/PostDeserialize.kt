package com.makentoshe.booruchan.core.post.deserialize

import com.makentoshe.booruchan.core.post.Post

interface PostDeserialize<out P : Post> {
    /** Deserialized from [rawValue] object */
    val post: P

    /** An object representation in string (json, xml) */
    val rawValue: String
}