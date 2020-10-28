package com.makentoshe.booruchan.core.post

interface PostId {
    val postId: Int
}

fun postId(id: Int) = object : PostId {
    override val postId = id
}

