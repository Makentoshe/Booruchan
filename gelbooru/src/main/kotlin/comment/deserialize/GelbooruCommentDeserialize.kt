package comment.deserialize

import comment.GelbooruComment
import comment.XmlGelbooruComment

/** Json does not supported. 15.10.20 */
//typealias JsonGelbooruCommentDeserialize = GelbooruCommentDeserialize<JsonGelbooruComment>

typealias XmlGelbooruCommentDeserialize = GelbooruCommentDeserialize<XmlGelbooruComment>

data class GelbooruCommentDeserialize<out Comment: GelbooruComment>(
    override val comment: Comment
): CommentDeserialize<Comment>
