package tag.deserialize

import tag.DanbooruTag
import tag.JsonDanbooruTag
import tag.XmlDanbooruTag

abstract class DanbooruTagsDeserialize<out P : DanbooruTag> {

    /**
     * Contains tags and failures in one structure.
     * Here we can understand not only "success" or "failure", but position as well
     */
    abstract val rawTags: List<Any>

    abstract val tags: List<P>

    abstract val failures: List<Map<String, Any?>>

}

class XmlDanbooruTagsDeserialize(
    deserializes: List<XmlDanbooruTagDeserialize>
) : DanbooruTagsDeserialize<XmlDanbooruTag>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override val rawTags = deserializes.map {
        when (it) {
            is XmlDanbooruTagDeserialize.Success -> it.tag
            is XmlDanbooruTagDeserialize.Failure -> it.raw
        }
    }

    override val tags = rawTags.filterIsInstance<XmlDanbooruTag>()

    override val failures = rawTags.filterIsInstance<Map<String, Any?>>()

}

class JsonDanbooruTagsDeserialize(
    deserializes: List<JsonDanbooruTagDeserialize>
) : DanbooruTagsDeserialize<JsonDanbooruTag>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override val rawTags = deserializes.map {
        when (it) {
            is JsonDanbooruTagDeserialize.Success -> it.tag
            is JsonDanbooruTagDeserialize.Failure -> it.raw
        }
    }

    override val tags = rawTags.filterIsInstance<JsonDanbooruTag>()

    override val failures = rawTags.filterIsInstance<Map<String, Any?>>()
}

