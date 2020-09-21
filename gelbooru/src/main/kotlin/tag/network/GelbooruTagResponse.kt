package tag.network

interface GelbooruTagResponse {
    interface Success : GelbooruTagResponse {
        val string: String
    }

    interface Failure : GelbooruTagResponse {
        val exception: Exception
    }
}

sealed class XmlGelbooruTagResponse : GelbooruTagResponse {

    data class Success(override val string: String) : XmlGelbooruTagResponse(), GelbooruTagResponse.Success

    data class Failure(override val exception: Exception) : XmlGelbooruTagResponse(), GelbooruTagResponse.Failure
}

sealed class JsonGelbooruTagResponse : GelbooruTagResponse {

    data class Success(override val string: String) : JsonGelbooruTagResponse(), GelbooruTagResponse.Success

    data class Failure(override val exception: Exception) : JsonGelbooruTagResponse(), GelbooruTagResponse.Failure
}
