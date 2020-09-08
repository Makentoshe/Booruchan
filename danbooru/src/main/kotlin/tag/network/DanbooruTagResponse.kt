package tag.network

interface DanbooruTagResponse {
    interface Success : DanbooruTagResponse {
        val string: String
    }

    interface Failure : DanbooruTagResponse {
        val exception: Exception
    }
}

sealed class XmlDanbooruTagResponse : DanbooruTagResponse {

    data class Success(override val string: String) : XmlDanbooruTagResponse(), DanbooruTagResponse.Success

    data class Failure(override val exception: Exception) : XmlDanbooruTagResponse(), DanbooruTagResponse.Failure
}

sealed class JsonDanbooruTagResponse : DanbooruTagResponse {

    data class Success(override val string: String) : JsonDanbooruTagResponse(), DanbooruTagResponse.Success

    data class Failure(override val exception: Exception) : JsonDanbooruTagResponse(), DanbooruTagResponse.Failure
}
