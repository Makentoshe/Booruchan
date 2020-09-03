package post.network

interface DanbooruPostResponse {
    interface Success : DanbooruPostResponse {
        val string: String
    }

    interface Failure : DanbooruPostResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruPostResponse : DanbooruPostResponse {

    data class Success(override val string: String) : XmlDanbooruPostResponse(), DanbooruPostResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruPostResponse(), DanbooruPostResponse.Failure
}

sealed class JsonDanbooruPostResponse : DanbooruPostResponse {

    data class Success(override val string: String) : JsonDanbooruPostResponse(), DanbooruPostResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruPostResponse(), DanbooruPostResponse.Failure
}
