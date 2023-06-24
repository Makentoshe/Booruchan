package com.makentoshe.booruchan.core.comment.deserialize

import com.makentoshe.booruchan.core.comment.Comment
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException

interface CommentsDeserialize<out C : Comment> {
    val deserializes: List<Result<CommentDeserialize<C>>>
    val comments: List<C>
    val failures: List<EntityDeserializeException>
}