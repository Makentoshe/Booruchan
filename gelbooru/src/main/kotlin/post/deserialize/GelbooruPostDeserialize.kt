package post.deserialize

import post.GelbooruPost
import post.JsonGelbooruPost
import post.XmlGelbooruPost

interface GelbooruPostDeserialize {

    interface Success<out Post : GelbooruPost> : GelbooruPostDeserialize {
        val post: Post
    }

    interface Failure : GelbooruPostDeserialize {
        val raw: Map<String, Any?>
        val exception: Exception
    }
}

sealed class XmlGelbooruPostDeserialize : GelbooruPostDeserialize {

    data class Success(
        override val post: XmlGelbooruPost
    ) : XmlGelbooruPostDeserialize(), GelbooruPostDeserialize.Success<XmlGelbooruPost>

    data class Failure(
        override val raw: Map<String, Any?>,
        override val exception: Exception
    ) : XmlGelbooruPostDeserialize(), GelbooruPostDeserialize.Failure

}

sealed class JsonGelbooruPostDeserialize : GelbooruPostDeserialize {

    data class Success(
        override val post: JsonGelbooruPost
    ) : JsonGelbooruPostDeserialize(), GelbooruPostDeserialize.Success<JsonGelbooruPost>

    data class Failure(
        override val raw: Map<String, Any?>,
        override val exception: Exception
    ) : JsonGelbooruPostDeserialize(), GelbooruPostDeserialize.Failure

}
