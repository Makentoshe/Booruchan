package comment

import CreatorId
import Text
import Time
import post.PostId

interface Comment : CommentId, Text, CreatorId, PostId {
    val creationTime: Time
}