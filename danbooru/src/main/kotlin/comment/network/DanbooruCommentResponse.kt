package comment.network

interface DanbooruCommentResponse {
    interface Success : DanbooruCommentResponse {
        val string: String
    }

    interface Failure : DanbooruCommentResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruCommentResponse : DanbooruCommentResponse {

    data class Success(override val string: String) : XmlDanbooruCommentResponse(), DanbooruCommentResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruCommentResponse(), DanbooruCommentResponse.Failure
}

sealed class JsonDanbooruCommentResponse : DanbooruCommentResponse {

    data class Success(override val string: String) : JsonDanbooruCommentResponse(), DanbooruCommentResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruCommentResponse(), DanbooruCommentResponse.Failure
}
