package comment.network

interface GelbooruCommentResponse {
    interface Success : GelbooruCommentResponse {
        val string: String
    }

    interface Failure : GelbooruCommentResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruCommentResponse : GelbooruCommentResponse {

    data class Success(override val string: String) : XmlGelbooruCommentResponse(), GelbooruCommentResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruCommentResponse(), GelbooruCommentResponse.Failure
}
