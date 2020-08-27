import comment.Comment

data class GelbooruComment(
    override val raw: Map<String, String>,
    override val commentId: Int,
    override val text: String,
    override val creationTime: Time,
    override val creatorId: Int,
    override val postId: Int
) : Comment