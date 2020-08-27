import comment.Comment

data class DanbooruComment(
    override val raw: Map<String, String>,
    override val commentId: Int,
    override val postId: Int,
    override val creatorId: Int,
    override val text: String,
    override val creationTime: Time,
    override val score: Int,
    val isSticky: Boolean,
    val isDeleted: Boolean,
    /** Inverse "do_not_bump_post" value */
    val isBumped: Boolean,
    val updaterId: Int,
    val updationTime: Time
) : Comment, Score