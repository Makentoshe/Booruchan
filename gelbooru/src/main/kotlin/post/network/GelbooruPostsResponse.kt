package post.network

interface GelbooruPostsResponse {
    interface Success : GelbooruPostsResponse {
        val string: String
    }

    interface Failure : GelbooruPostsResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruPostsResponse : GelbooruPostsResponse {

    data class Success(override val string: String) : XmlGelbooruPostsResponse(), GelbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruPostsResponse(), GelbooruPostsResponse.Failure
}

sealed class JsonGelbooruPostsResponse : GelbooruPostsResponse {

    data class Success(override val string: String) : JsonGelbooruPostsResponse(), GelbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruPostsResponse(), GelbooruPostsResponse.Failure
}

