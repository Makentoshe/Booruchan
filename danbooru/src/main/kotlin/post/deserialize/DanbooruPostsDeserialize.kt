package post.deserialize

import post.DanbooruPost
import post.JsonDanbooruPost
import post.XmlDanbooruPost

abstract class DanbooruPostsDeserialize<out P : DanbooruPost> {

    /**
     * Contains posts and failures in one structure.
     * Here we can understand not only "success" or "failure", but position as well
     */
    // todo mb replace by list of deserializes
    abstract val rawPosts: List<Any>

    abstract val posts: List<P>

    abstract val failures: List<Map<String, Any?>>

}

class XmlDanbooruPostsDeserialize(
    val deserializes: List<XmlDanbooruPostDeserialize>
) : DanbooruPostsDeserialize<XmlDanbooruPost>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override val rawPosts = deserializes.map {
        when (it) {
            is XmlDanbooruPostDeserialize.Success -> it.post
            is XmlDanbooruPostDeserialize.Failure -> it.raw
        }
    }

    override val posts = rawPosts.filterIsInstance<XmlDanbooruPost>()

    override val failures = rawPosts.filterIsInstance<Map<String, Any?>>()

}

class JsonDanbooruPostsDeserialize(
    val deserializes: List<JsonDanbooruPostDeserialize>
) : DanbooruPostsDeserialize<JsonDanbooruPost>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override val rawPosts = deserializes.map {
        when (it) {
            is JsonDanbooruPostDeserialize.Success -> it.post
            is JsonDanbooruPostDeserialize.Failure -> it.raw
        }
    }

    override val posts = rawPosts.filterIsInstance<JsonDanbooruPost>()

    override val failures = rawPosts.filterIsInstance<Map<String, Any?>>()
}

