package tag.deserialize

import tag.DanbooruTag
import tag.JsonDanbooruTag
import tag.XmlDanbooruTag

interface DanbooruTagDeserialize {

    interface Success<out Tag : DanbooruTag> : DanbooruTagDeserialize {
        val tag: Tag
    }

    interface Failure : DanbooruTagDeserialize {
        val raw: Map<String, Any?>
    }
}

sealed class XmlDanbooruTagDeserialize : DanbooruTagDeserialize {

    data class Success(
        override val tag: XmlDanbooruTag
    ) : XmlDanbooruTagDeserialize(), DanbooruTagDeserialize.Success<XmlDanbooruTag>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : XmlDanbooruTagDeserialize(), DanbooruTagDeserialize.Failure

}

sealed class JsonDanbooruTagDeserialize : DanbooruTagDeserialize {

    data class Success(
        override val tag: JsonDanbooruTag
    ) : JsonDanbooruTagDeserialize(), DanbooruTagDeserialize.Success<JsonDanbooruTag>

    data class Failure(
        override val raw: Map<String, Any?>
    ) : JsonDanbooruTagDeserialize(), DanbooruTagDeserialize.Failure

}
