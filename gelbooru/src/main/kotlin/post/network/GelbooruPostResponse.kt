package post.network

interface GelbooruPostResponse {
    interface Success: GelbooruPostResponse {
        val string: String
    }
    interface Failure: GelbooruPostResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruPostResponse: GelbooruPostResponse {

    data class Success(override val string: String) : XmlGelbooruPostResponse(), GelbooruPostResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruPostResponse(), GelbooruPostResponse.Failure
}

sealed class JsonGelbooruPostResponse: GelbooruPostResponse {

    data class Success(override val string: String) : JsonGelbooruPostResponse(), GelbooruPostResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruPostResponse(), GelbooruPostResponse.Failure
}
