package comment.network

interface GelbooruCommentsResponse {
    interface Success : GelbooruCommentsResponse {
        val string: String
    }

    interface Failure : GelbooruCommentsResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruCommentsResponse : GelbooruCommentsResponse {

    data class Success(override val string: String) : XmlGelbooruCommentsResponse(), GelbooruCommentsResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruCommentsResponse(), GelbooruCommentsResponse.Failure
}

sealed class JsonGelbooruCommentsResponse : GelbooruCommentsResponse {

    data class Success(override val string: String) : JsonGelbooruCommentsResponse(), GelbooruCommentsResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruCommentsResponse(), GelbooruCommentsResponse.Failure
}
