package comment.deserialize

import comment.GelbooruComment
import comment.XmlGelbooruComment

interface GelbooruCommentDeserialize {

    interface Success<out Comment : GelbooruComment> : GelbooruCommentDeserialize {
        val comment: Comment
    }

    interface Failure : GelbooruCommentDeserialize {
        val raw: Map<String, Any?>
        val exception: Exception
    }
}

sealed class XmlGelbooruCommentDeserialize : GelbooruCommentDeserialize {

    data class Success(
        override val comment: XmlGelbooruComment
    ) : XmlGelbooruCommentDeserialize(), GelbooruCommentDeserialize.Success<XmlGelbooruComment>

    data class Failure(
        override val exception: Exception,
        override val raw: Map<String, Any?>
    ) : XmlGelbooruCommentDeserialize(), GelbooruCommentDeserialize.Failure

}
