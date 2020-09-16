package comment.deserialize

import comment.DanbooruComment
import comment.JsonDanbooruComment
import comment.XmlDanbooruComment

interface DanbooruCommentDeserialize {

    interface Success<out Comment : DanbooruComment> : DanbooruCommentDeserialize {
        val comment: Comment
    }

    interface Failure : DanbooruCommentDeserialize {
        val raw: Map<String, Any?>
    }
}

sealed class XmlDanbooruCommentDeserialize : DanbooruCommentDeserialize {

    data class Success(
        override val comment: XmlDanbooruComment
    ) : XmlDanbooruCommentDeserialize(), DanbooruCommentDeserialize.Success<XmlDanbooruComment>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : XmlDanbooruCommentDeserialize(), DanbooruCommentDeserialize.Failure

}

sealed class JsonDanbooruCommentDeserialize : DanbooruCommentDeserialize {

    data class Success(
        override val comment: JsonDanbooruComment
    ) : JsonDanbooruCommentDeserialize(), DanbooruCommentDeserialize.Success<JsonDanbooruComment>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : JsonDanbooruCommentDeserialize(), DanbooruCommentDeserialize.Failure

}
