package post.deserialize

import deserialize.EntityDeserializeException
import post.Post

interface PostsDeserialize<out P : Post> {
    val deserializes: List<Result<PostDeserialize<P>>>
    val posts: List<P>
    val failures: List<EntityDeserializeException>
}