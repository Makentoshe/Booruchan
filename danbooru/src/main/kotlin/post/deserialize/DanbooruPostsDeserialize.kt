package post.deserialize

import deserialize.EntityDeserializeException
import post.DanbooruPost

data class DanbooruPostsDeserialize<out Post : DanbooruPost>(
    override val deserializes: List<Result<DanbooruPostDeserialize<Post>>>
) : PostsDeserialize<Post> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val posts = deserializes.mapNotNull { it.getOrNull()?.post }
}
