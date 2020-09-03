package post.network

interface DanbooruPostsResponse {
    interface Success : DanbooruPostsResponse {
        val string: String
    }

    interface Failure : DanbooruPostsResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruPostsResponse : DanbooruPostsResponse {

    data class Success(override val string: String) : XmlDanbooruPostsResponse(), DanbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruPostsResponse(), DanbooruPostsResponse.Failure
}

sealed class JsonDanbooruPostsResponse : DanbooruPostsResponse {

    data class Success(override val string: String) : JsonDanbooruPostsResponse(), DanbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruPostsResponse(), DanbooruPostsResponse.Failure
}

