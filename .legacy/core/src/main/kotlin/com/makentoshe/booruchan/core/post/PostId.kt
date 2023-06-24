package com.makentoshe.booruchan.core.post

import java.io.Serializable

interface PostId: Serializable {
    val postId: Int
}

fun postId(id: Int) = object : PostId {
    override val postId = id
}

