package comment

import Time
import CreatorId
import Text
import post.PostId

interface Comment : CommentId, Text, CreatorId, PostId {
    val raw: Map<String, String>
    val creationTime: Time
}