package tag.deserialize

import tag.GelbooruTag
import tag.JsonGelbooruTag
import tag.XmlGelbooruTag

interface GelbooruTagDeserialize {

    interface Success<out Tag : GelbooruTag> : GelbooruTagDeserialize {
        val tag: Tag
    }

    interface Failure : GelbooruTagDeserialize {
        val raw: Map<String, Any?>
        val exception: Exception
    }
}

sealed class XmlGelbooruTagDeserialize : GelbooruTagDeserialize {

    data class Success(
        override val tag: XmlGelbooruTag
    ) : XmlGelbooruTagDeserialize(), GelbooruTagDeserialize.Success<XmlGelbooruTag>

    data class Failure(
        override val exception: Exception,
        override val raw: Map<String, Any?>
    ) : XmlGelbooruTagDeserialize(), GelbooruTagDeserialize.Failure

}

sealed class JsonGelbooruTagDeserialize : GelbooruTagDeserialize {

    data class Success(
        override val tag: JsonGelbooruTag
    ) : JsonGelbooruTagDeserialize(), GelbooruTagDeserialize.Success<JsonGelbooruTag>

    data class Failure(
        override val exception: Exception,
        override val raw: Map<String, Any?>
    ) : JsonGelbooruTagDeserialize(), GelbooruTagDeserialize.Failure

}
