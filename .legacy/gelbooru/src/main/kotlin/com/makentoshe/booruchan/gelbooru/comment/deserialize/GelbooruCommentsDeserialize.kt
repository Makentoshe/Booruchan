package com.makentoshe.booruchan.gelbooru.comment.deserialize

import com.makentoshe.booruchan.core.comment.deserialize.CommentsDeserialize
import com.makentoshe.booruchan.gelbooru.comment.GelbooruComment
import com.makentoshe.booruchan.gelbooru.comment.XmlGelbooruComment
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException

/** Json not supported. 15.10.20 */
//typealias JsonGelbooruCommentsDeserialize = GelbooruCommentsDeserialize<JsonGelbooruComment>

typealias XmlGelbooruCommentsDeserialize = GelbooruCommentsDeserialize<XmlGelbooruComment>

data class GelbooruCommentsDeserialize<out Comment : GelbooruComment>(
    override val deserializes: List<Result<GelbooruCommentDeserialize<Comment>>>
) : CommentsDeserialize<Comment> {
    override val comments = deserializes.mapNotNull { it.getOrNull()?.comment }
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
}
