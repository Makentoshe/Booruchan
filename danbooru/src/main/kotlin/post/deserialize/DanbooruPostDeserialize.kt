package post.deserialize

import post.DanbooruPost
import post.JsonDanbooruPost
import post.XmlDanbooruPost

interface DanbooruPostDeserialize {

    interface Success<out Post : DanbooruPost> : DanbooruPostDeserialize {
        val post: Post
    }

    interface Failure : DanbooruPostDeserialize {
        val raw: Map<String, Any?>
    }
}

sealed class XmlDanbooruPostDeserialize : DanbooruPostDeserialize {

    data class Success(
        override val post: XmlDanbooruPost
    ) : XmlDanbooruPostDeserialize(), DanbooruPostDeserialize.Success<XmlDanbooruPost>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : XmlDanbooruPostDeserialize(), DanbooruPostDeserialize.Failure

}

sealed class JsonDanbooruPostDeserialize : DanbooruPostDeserialize {

    data class Success(
        override val post: JsonDanbooruPost
    ) : JsonDanbooruPostDeserialize(), DanbooruPostDeserialize.Success<JsonDanbooruPost>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : JsonDanbooruPostDeserialize(), DanbooruPostDeserialize.Failure

}
