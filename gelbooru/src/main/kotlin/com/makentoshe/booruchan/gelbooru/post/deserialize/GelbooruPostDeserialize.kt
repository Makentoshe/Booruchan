package com.makentoshe.booruchan.gelbooru.post.deserialize

import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.gelbooru.post.GelbooruPost
import com.makentoshe.booruchan.gelbooru.post.JsonGelbooruPost
import com.makentoshe.booruchan.gelbooru.post.XmlGelbooruPost

typealias XmlGelbooruPostDeserialize = GelbooruPostDeserialize<XmlGelbooruPost>
typealias JsonGelbooruPostDeserialize = GelbooruPostDeserialize<JsonGelbooruPost>

data class GelbooruPostDeserialize<out Post: GelbooruPost>(
    override val post: Post
): PostDeserialize<Post>
