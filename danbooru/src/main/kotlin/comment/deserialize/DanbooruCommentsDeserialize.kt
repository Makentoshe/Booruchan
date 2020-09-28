package comment.deserialize

import comment.DanbooruComment
import comment.JsonDanbooruComment
import comment.XmlDanbooruComment
import deserialize.EntityDeserializeException

typealias JsonDanbooruCommentsDeserialize = DanbooruCommentsDeserialize<JsonDanbooruComment>
typealias XmlDanbooruCommentsDeserialize = DanbooruCommentsDeserialize<XmlDanbooruComment>

data class DanbooruCommentsDeserialize<out Comment : DanbooruComment>(
    override val deserializes: List<Result<DanbooruCommentDeserialize<Comment>>>
) : CommentsDeserialize<Comment> {
    override val comments = deserializes.mapNotNull { it.getOrNull()?.comment }
    override val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }
}
