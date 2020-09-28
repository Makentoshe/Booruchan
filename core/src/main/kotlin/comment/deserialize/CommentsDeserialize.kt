package comment.deserialize

import comment.Comment
import deserialize.EntityDeserializeException

interface CommentsDeserialize<out C : Comment> {
    val deserializes: List<Result<CommentDeserialize<C>>>
    val comments: List<C>
    val failures: List<EntityDeserializeException>
}