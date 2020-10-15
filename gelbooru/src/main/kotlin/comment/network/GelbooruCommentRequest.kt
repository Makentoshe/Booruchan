package comment.network

import network.GelbooruRequest

@Deprecated(message = "This api does not supports", level = DeprecationLevel.ERROR)
object GelbooruCommentRequest: GelbooruRequest(), CommentRequest {
    override val url = host
}