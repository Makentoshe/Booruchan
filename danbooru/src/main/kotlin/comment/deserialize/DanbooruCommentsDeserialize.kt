package comment.deserialize

import comment.DanbooruComment
import deserialize.EntityDeserializeException

data class DanbooruCommentsDeserialize<out Comment: DanbooruComment>(
    val deserializes: List<Result<DanbooruCommentDeserialize<Comment>>>
) {
    val failures = deserializes.mapNotNull { it.exceptionOrNull() as? EntityDeserializeException }

    val comments = deserializes.mapNotNull { it.getOrNull()?.comment }
}
