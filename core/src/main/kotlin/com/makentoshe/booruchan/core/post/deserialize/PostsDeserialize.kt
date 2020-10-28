package com.makentoshe.booruchan.core.post.deserialize

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.core.post.Post

interface PostsDeserialize<out P : Post> {
    val deserializes: List<Result<PostDeserialize<P>>>
    val posts: List<P>
    val failures: List<EntityDeserializeException>
}