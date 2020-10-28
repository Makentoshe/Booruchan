package com.makentoshe.booruchan.danbooru.post.deserialize

import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.danbooru.post.DanbooruPost

data class DanbooruPostsDeserialize<out Post : DanbooruPost>(
    override val deserializes: List<Result<DanbooruPostDeserialize<Post>>>
) : PostsDeserialize<Post> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val posts = deserializes.mapNotNull { it.getOrNull()?.post }
}
