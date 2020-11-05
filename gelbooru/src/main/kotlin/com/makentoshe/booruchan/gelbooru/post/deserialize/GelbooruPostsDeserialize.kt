package com.makentoshe.booruchan.gelbooru.post.deserialize

import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.gelbooru.post.GelbooruPost
import com.makentoshe.booruchan.gelbooru.post.JsonGelbooruPost
import com.makentoshe.booruchan.gelbooru.post.XmlGelbooruPost

typealias XmlGelbooruPostsDeserialize = GelbooruPostsDeserialize<XmlGelbooruPost>
typealias JsonGelbooruPostsDeserialize = GelbooruPostsDeserialize<JsonGelbooruPost>

data class GelbooruPostsDeserialize<out Post : GelbooruPost>(
    override val deserializes: List<Result<GelbooruPostDeserialize<Post>>>, override val rawValue: String
) : PostsDeserialize<Post> {
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
    override val posts = deserializes.mapNotNull { it.getOrNull()?.post }
}
