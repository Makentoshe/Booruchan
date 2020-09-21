package tag.network

interface GelbooruTagsResponse {
    interface Success : GelbooruTagsResponse {
        val string: String
    }

    interface Failure : GelbooruTagsResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruTagsResponse : GelbooruTagsResponse {

    data class Success(override val string: String) : XmlGelbooruTagsResponse(), GelbooruTagsResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruTagsResponse(), GelbooruTagsResponse.Failure
}

sealed class JsonGelbooruTagsResponse : GelbooruTagsResponse {

    data class Success(override val string: String) : JsonGelbooruTagsResponse(), GelbooruTagsResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruTagsResponse(), GelbooruTagsResponse.Failure
}
