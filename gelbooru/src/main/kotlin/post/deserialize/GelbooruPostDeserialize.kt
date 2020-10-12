package post.deserialize

import post.GelbooruPost
import post.JsonGelbooruPost
import post.XmlGelbooruPost

typealias XmlGelbooruPostDeserialize = GelbooruPostDeserialize<XmlGelbooruPost>
typealias JsonGelbooruPostDeserialize = GelbooruPostDeserialize<JsonGelbooruPost>

data class GelbooruPostDeserialize<out Post: GelbooruPost>(
    override val post: Post
): PostDeserialize<Post>
