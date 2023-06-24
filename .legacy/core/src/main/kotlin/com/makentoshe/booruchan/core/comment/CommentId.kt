package com.makentoshe.booruchan.core.comment

import java.io.Serializable

interface CommentId: Serializable {
    val commentId: Int
}

fun commentId(id: Int) = object : CommentId {
    override val commentId = id
}