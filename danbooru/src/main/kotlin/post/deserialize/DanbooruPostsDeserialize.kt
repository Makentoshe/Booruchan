package post.deserialize

import deserialize.DeserializeException
import post.DanbooruPost

data class DanbooruPostsDeserialize<out Post : DanbooruPost>(
    val deserializes: List<Result<DanbooruPostDeserialize<Post>>>
) {
    val failures = deserializes.mapNotNull { it.exceptionOrNull() as? DeserializeException }

    val posts = deserializes.mapNotNull { it.getOrNull()?.post }
}
