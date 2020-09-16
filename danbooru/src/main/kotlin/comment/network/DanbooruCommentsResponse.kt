package comment.network

interface DanbooruCommentsResponse {
    interface Success : DanbooruCommentsResponse {
        val string: String
    }

    interface Failure : DanbooruCommentsResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruCommentsResponse : DanbooruCommentsResponse {

    data class Success(override val string: String) : XmlDanbooruCommentsResponse(), DanbooruCommentsResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruCommentsResponse(), DanbooruCommentsResponse.Failure
}

sealed class JsonDanbooruCommentsResponse : DanbooruCommentsResponse {

    data class Success(override val string: String) : JsonDanbooruCommentsResponse(), DanbooruCommentsResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruCommentsResponse(), DanbooruCommentsResponse.Failure
}
