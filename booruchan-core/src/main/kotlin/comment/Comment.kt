package comment

import CreationTime
import CreatorId
import Text
import post.PostId

interface Comment : CommentId, Text, CreatorId, PostId {
    val raw: Map<String, String>
    val creationTime: CreationTime
}