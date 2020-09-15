package tag.deserialize

import tag.DanbooruTag

interface DanbooruTagsDeserialize {

    val deserializes: List<DanbooruTagDeserialize>

    val tags: List<DanbooruTagDeserialize.Success<DanbooruTag>>

    val failures: List<DanbooruTagDeserialize.Failure>
}

class XmlDanbooruTagsDeserialize(
    override val deserializes: List<XmlDanbooruTagDeserialize>
) : DanbooruTagsDeserialize {
    override val tags = deserializes.filterIsInstance<XmlDanbooruTagDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlDanbooruTagDeserialize.Failure>()
}

class JsonDanbooruTagsDeserialize(
    override val deserializes: List<JsonDanbooruTagDeserialize>
) : DanbooruTagsDeserialize {
    override val tags = deserializes.filterIsInstance<JsonDanbooruTagDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<JsonDanbooruTagDeserialize.Failure>()
}

