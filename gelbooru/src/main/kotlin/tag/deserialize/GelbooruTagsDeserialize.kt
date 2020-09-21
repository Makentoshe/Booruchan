package tag.deserialize

import tag.GelbooruTag

interface GelbooruTagsDeserialize {

    val deserializes: List<GelbooruTagDeserialize>

    val tags: List<GelbooruTagDeserialize.Success<GelbooruTag>>

    val failures: List<GelbooruTagDeserialize.Failure>
}

class XmlGelbooruTagsDeserialize(
    override val deserializes: List<XmlGelbooruTagDeserialize>
) : GelbooruTagsDeserialize {
    override val tags = deserializes.filterIsInstance<XmlGelbooruTagDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlGelbooruTagDeserialize.Failure>()
}

class JsonGelbooruTagsDeserialize(
    override val deserializes: List<JsonGelbooruTagDeserialize>
) : GelbooruTagsDeserialize {
    override val tags = deserializes.filterIsInstance<JsonGelbooruTagDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<JsonGelbooruTagDeserialize.Failure>()
}

