package com.makentoshe.booruchan.danbooru.comment.deserialize

import com.makentoshe.booruchan.core.comment.deserialize.CommentsDeserialize
import com.makentoshe.booruchan.danbooru.comment.DanbooruComment
import com.makentoshe.booruchan.danbooru.comment.JsonDanbooruComment
import com.makentoshe.booruchan.danbooru.comment.XmlDanbooruComment
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException

typealias JsonDanbooruCommentsDeserialize = DanbooruCommentsDeserialize<JsonDanbooruComment>
typealias XmlDanbooruCommentsDeserialize = DanbooruCommentsDeserialize<XmlDanbooruComment>

data class DanbooruCommentsDeserialize<out Comment : DanbooruComment>(
    override val deserializes: List<Result<DanbooruCommentDeserialize<Comment>>>
) : CommentsDeserialize<Comment> {
    override val comments = deserializes.mapNotNull { it.getOrNull()?.comment }
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
}
