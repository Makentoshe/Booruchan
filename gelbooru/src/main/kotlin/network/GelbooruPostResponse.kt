package network

interface GelbooruPostResponse {
    interface Success: GelbooruPostResponse {
        val string: String
    }
    interface Failure: GelbooruPostResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruPostResponse: GelbooruPostResponse {

    data class Success(override val string: String) : XmlGelbooruPostResponse(), GelbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruPostResponse(), GelbooruPostsResponse.Failure
}

sealed class JsonGelbooruPostResponse: GelbooruPostResponse {

    data class Success(override val string: String) : JsonGelbooruPostResponse(), GelbooruPostsResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruPostResponse(), GelbooruPostsResponse.Failure
}
