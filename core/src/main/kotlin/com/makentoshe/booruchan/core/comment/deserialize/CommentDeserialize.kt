package com.makentoshe.booruchan.core.comment.deserialize

import com.makentoshe.booruchan.core.comment.Comment

interface CommentDeserialize<out C : Comment> {
    val comment: C
}

