package tag.network

interface DanbooruTagsResponse {
    interface Success : DanbooruTagsResponse {
        val string: String
    }

    interface Failure : DanbooruTagsResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruTagsResponse : DanbooruTagsResponse {

    data class Success(override val string: String) : XmlDanbooruTagsResponse(), DanbooruTagsResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruTagsResponse(), DanbooruTagsResponse.Failure
}

sealed class JsonDanbooruTagsResponse : DanbooruTagsResponse {

    data class Success(override val string: String) : JsonDanbooruTagsResponse(), DanbooruTagsResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruTagsResponse(), DanbooruTagsResponse.Failure
}
