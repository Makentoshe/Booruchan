package comment.deserialize

import comment.GelbooruComment

interface GelbooruCommentsDeserialize {

    val deserializes: List<GelbooruCommentDeserialize>

    val comments: List<GelbooruCommentDeserialize.Success<GelbooruComment>>

    val failures: List<GelbooruCommentDeserialize.Failure>
}

class XmlGelbooruCommentsDeserialize(
    override val deserializes: List<XmlGelbooruCommentDeserialize>
) : GelbooruCommentsDeserialize {
    override val comments = deserializes.filterIsInstance<XmlGelbooruCommentDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlGelbooruCommentDeserialize.Failure>()
}
