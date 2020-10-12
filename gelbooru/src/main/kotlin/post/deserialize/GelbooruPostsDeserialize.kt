package post.deserialize

import deserialize.EntityDeserializeException
import post.GelbooruPost
import post.JsonGelbooruPost
import post.XmlGelbooruPost

typealias XmlGelbooruPostsDeserialize = GelbooruPostsDeserialize<XmlGelbooruPost>
typealias JsonGelbooruPostsDeserialize = GelbooruPostsDeserialize<JsonGelbooruPost>

data class GelbooruPostsDeserialize<out Post : GelbooruPost>(
    override val deserializes: List<Result<GelbooruPostDeserialize<Post>>>
) : PostsDeserialize<Post> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val posts = deserializes.mapNotNull { it.getOrNull()?.post }
}
