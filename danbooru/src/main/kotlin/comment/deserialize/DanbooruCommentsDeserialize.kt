package comment.deserialize

import comment.DanbooruComment

interface DanbooruCommentsDeserialize {

    val deserializes: List<DanbooruCommentDeserialize>

    val comments: List<DanbooruCommentDeserialize.Success<DanbooruComment>>

    val failures: List<DanbooruCommentDeserialize.Failure>
}

class XmlDanbooruCommentsDeserialize(
    override val deserializes: List<XmlDanbooruCommentDeserialize>
) : DanbooruCommentsDeserialize {
    override val comments = deserializes.filterIsInstance<XmlDanbooruCommentDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlDanbooruCommentDeserialize.Failure>()
}

class JsonDanbooruCommentsDeserialize(
    override val deserializes: List<JsonDanbooruCommentDeserialize>
) : DanbooruCommentsDeserialize {
    override val comments = deserializes.filterIsInstance<JsonDanbooruCommentDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<JsonDanbooruCommentDeserialize.Failure>()
}
