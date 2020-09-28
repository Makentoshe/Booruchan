package comment.deserialize

import comment.DanbooruComment
import comment.JsonDanbooruComment
import comment.XmlDanbooruComment

typealias JsonDanbooruCommentDeserialize = DanbooruCommentDeserialize<JsonDanbooruComment>
typealias XmlDanbooruCommentDeserialize = DanbooruCommentDeserialize<XmlDanbooruComment>

data class DanbooruCommentDeserialize<out Comment: DanbooruComment>(
    override val comment: Comment
): CommentDeserialize<Comment>
