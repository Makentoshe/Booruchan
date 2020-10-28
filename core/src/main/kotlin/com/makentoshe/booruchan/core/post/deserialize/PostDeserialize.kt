package com.makentoshe.booruchan.core.post.deserialize

import com.makentoshe.booruchan.core.post.Post

interface PostDeserialize<out P : Post> {
    val post: P
}