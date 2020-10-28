package com.makentoshe.booruchan.danbooru.post.deserialize

import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.danbooru.post.DanbooruPost

data class DanbooruPostDeserialize<out Post: DanbooruPost>(
    override val post: Post
): PostDeserialize<Post>
